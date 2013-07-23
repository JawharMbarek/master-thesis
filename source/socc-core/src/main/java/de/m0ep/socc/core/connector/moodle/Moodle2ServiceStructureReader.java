/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller Permission is hereby granted,
 * free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the “Software”), to deal in the Software
 * without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions: The above copyright notice and this
 * permission notice shall be included in all copies or substantial portions of
 * the Software. THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package de.m0ep.socc.core.connector.moodle;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import de.m0ep.moodlews.soap.ForumDiscussionRecord;
import de.m0ep.moodlews.soap.ForumRecord;
import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IServiceStructureReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.RdfUtils;

public class Moodle2ServiceStructureReader implements IServiceStructureReader {
    private Moodle2Connector connector;
    private Moodle2ClientWrapper client;
    private Model model;
    private URI serviceEndpoint;

    public Moodle2ServiceStructureReader(Moodle2Connector connector) {
        Preconditions.checkNotNull(connector,
                "Required parameter connector must be specified.");

        this.connector = connector;
        this.serviceEndpoint = this.connector.getService().getServiceEndpoint().asURI();
        this.client = (Moodle2ClientWrapper) this.connector.getServiceClientManager()
                .getDefaultClient();
        this.model = this.connector.getContext().getModel();
    }

    @Override
    public IConnector getConnector() {
        return connector;
    }

    @Override
    public Site getSite() {
        if (!Site.hasInstance(model, serviceEndpoint)) {
            Site result = new Site(model, serviceEndpoint, true);
            result.setName("Moolde LMS (" + serviceEndpoint.toString() + ")");
            return result;
        }

        return Site.getInstance(connector.getContext().getModel(), serviceEndpoint);
    }

    @Override
    public Forum getForum(final String id) throws NotFoundException, AuthenticationException,
            IOException {
        Preconditions.checkNotNull(id,
                "Required parameter id must be specified.");
        Preconditions.checkArgument(!id.isEmpty(),
                "Required parameter id may not be empty.");

        ForumRecord[] forumRecordArray = client.callMethod(new Callable<ForumRecord[]>() {
            @Override
            public ForumRecord[] call() throws Exception {
                return client.getBindingStub().get_all_forums(
                        client.getAuthClient(),
                        client.getSessionKey(),
                        "id",
                        id);
            }
        });

        if (null != forumRecordArray && 0 < forumRecordArray.length) {
            return Moodle2SiocConverter.createSiocForum(connector, forumRecordArray[0]);
        }

        throw new NotFoundException("No forum found with id " + id);
    }

    @Override
    public List<Forum> listForums() throws AuthenticationException, IOException {
        List<Forum> result = Lists.newArrayList();

        ForumRecord[] forumRecordArray = client.callMethod(new Callable<ForumRecord[]>() {
            @Override
            public ForumRecord[] call() throws Exception {
                return client.getBindingStub().get_all_forums(
                        client.getAuthClient(),
                        client.getSessionKey(),
                        "",
                        "");
            }
        });

        if (null != forumRecordArray && 0 < forumRecordArray.length) {

            for (ForumRecord forumRecord : forumRecordArray) {
                result.add(Moodle2SiocConverter.createSiocForum(connector, forumRecord));
            }
        }

        return result;
    }

    @Override
    public Thread getThread(String id, Container parent) throws NotFoundException,
            AuthenticationException, IOException {
        Preconditions.checkNotNull(id,
                "Required parameter id must be specified.");
        Preconditions.checkArgument(!id.isEmpty(),
                "Required parameter id may not be empty.");
        Preconditions.checkNotNull(parent,
                "Required parameter parent must be specified.");
        Preconditions.checkArgument(RdfUtils.isType(parent.getModel(), parent, Forum.RDFS_CLASS),
                "The parameter parent is not sioc:Forum");
        Preconditions.checkArgument(parent.toString().startsWith(serviceEndpoint.toString()),
                "The parent is no moodle forum.");
        Preconditions.checkArgument(parent.hasId(),
                "The parent has no id.");

        final int threadId;
        try {
            threadId = Integer.parseInt(id);
        } catch (NumberFormatException e1) {
            throw new IllegalArgumentException(
                    "The id is invalid: was " + id);
        }

        final int forumId;
        try {
            forumId = Integer.parseInt(parent.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The parent id is invalid: was " + parent.getId());
        }

        ForumDiscussionRecord[] discussionRecordArray = client.callMethod(
                new Callable<ForumDiscussionRecord[]>() {
                    @Override
                    public ForumDiscussionRecord[] call() throws Exception {
                        return client.getBindingStub().get_forum_discussions(
                                client.getAuthClient(),
                                client.getSessionKey(),
                                forumId,
                                9999);
                    }
                });

        if (null != discussionRecordArray && 0 < discussionRecordArray.length) {
            for (ForumDiscussionRecord discussionRecord : discussionRecordArray) {
                if (threadId == discussionRecord.getId()) {
                    return Moodle2SiocConverter.createSiocThread(
                            connector,
                            discussionRecord,
                            Forum.getInstance(
                                    parent.getModel(),
                                    parent.getResource()));
                }
            }
        }

        throw new NotFoundException("No thread found with id " + id);
    }

    @Override
    public List<Thread> listThreads(Container parent) throws AuthenticationException, IOException {
        Preconditions.checkNotNull(parent,
                "Required parameter parent must be specified.");
        Preconditions.checkArgument(RdfUtils.isType(parent.getModel(), parent, Forum.RDFS_CLASS),
                "The parameter parent is not sioc:Forum");
        Preconditions.checkArgument(parent.toString().startsWith(serviceEndpoint.toString()),
                "The parent is no moodle forum.");
        Preconditions.checkArgument(parent.hasId(),
                "The parent has no id.");

        List<Thread> result = Lists.newArrayList();

        final int forumId;
        try {
            forumId = Integer.parseInt(parent.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The parent id is invalid: was " + parent.getId());
        }

        ForumDiscussionRecord[] discussionRecordArray = client.callMethod(
                new Callable<ForumDiscussionRecord[]>() {
                    @Override
                    public ForumDiscussionRecord[] call() throws Exception {
                        return client.getBindingStub().get_forum_discussions(
                                client.getAuthClient(),
                                client.getSessionKey(),
                                forumId,
                                9999);
                    }
                });

        if (null != discussionRecordArray && 0 < discussionRecordArray.length) {
            for (ForumDiscussionRecord discussionRecord : discussionRecordArray) {
                result.add(Moodle2SiocConverter.createSiocThread(
                        connector,
                        discussionRecord,
                        Forum.getInstance(
                                parent.getModel(),
                                parent.getResource())));
            }
        }

        return result;
    }
}
