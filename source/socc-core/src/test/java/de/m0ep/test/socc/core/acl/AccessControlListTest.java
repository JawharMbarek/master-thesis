/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.m0ep.test.socc.core.acl;

import java.util.EnumSet;

import org.junit.Assert;
import org.junit.Test;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.ontoware.rdfreactor.schema.rdfs.Class;
import org.rdfs.sioc.Post;
import org.w3.ns.auth.acl.Authorization;

import com.xmlns.foaf.Agent;

import de.m0ep.socc.core.acl.Access;
import de.m0ep.socc.core.acl.AccessControl;
import de.m0ep.socc.core.acl.IAccessControl;

public class AccessControlListTest {

    private static final URI SOCC_BOT_AGENT_URI = Builder
            .createURI("http://example.com/agent/soccBot");
    private static final URI SAMPLE_USERACCOUNT_URI = Builder
            .createURI("http://example.com/agent/johnDoe");
    private static final URI SAMPLE_USERACCOUNT2_URI = Builder
            .createURI("http://example.com/agent/jennyDoe");
    private static final URI SAMPLE_RESOURCE_URI = Builder
            .createURI("http://example.com/resource");
    private static final URI SAMPLE_CLASS_URI = Post.RDFS_CLASS;

    @Test
    public void testCheckAuthorizationForResource() {
        Model model = createFilledModel();

        IAccessControl instance = new AccessControl(model, new Agent(model, SOCC_BOT_AGENT_URI, false));
        Agent user = Agent.getInstance(model, SAMPLE_USERACCOUNT_URI);

        Assert.assertTrue(instance.checkAuthorizationForResource(
                user,
                SAMPLE_RESOURCE_URI,
                EnumSet.of(Access.READ)));

        Assert.assertTrue(instance.checkAuthorizationForResource(
                user,
                SAMPLE_RESOURCE_URI,
                EnumSet.of(Access.READ, Access.WRITE)));

        Assert.assertFalse(instance.checkAuthorizationForResource(
                user,
                SAMPLE_RESOURCE_URI,
                EnumSet.of(Access.APPEND)));

        Assert.assertFalse(instance.checkAuthorizationForResource(
                user,
                SAMPLE_RESOURCE_URI,
                EnumSet.of(Access.WRITE, Access.CONTROL)));

        model.close();
    }

    @Test
    public void testCheckAuthorizationForClass() {
        Model model = createFilledModel();

        IAccessControl acl = new AccessControl(model, new Agent(model, SOCC_BOT_AGENT_URI, false));
        Agent user = Agent.getInstance(model, SAMPLE_USERACCOUNT_URI);

        Assert.assertTrue(acl.checkAuthorizationForClass(
                user,
                SAMPLE_CLASS_URI,
                EnumSet.of(Access.READ)));

        Assert.assertFalse(acl.checkAuthorizationForClass(
                user,
                SAMPLE_CLASS_URI,
                EnumSet.of(Access.READ, Access.WRITE)));

        Assert.assertFalse(acl.checkAuthorizationForClass(
                user,
                SAMPLE_CLASS_URI,
                EnumSet.of(Access.APPEND)));

        Assert.assertFalse(acl.checkAuthorizationForClass(
                user,
                SAMPLE_CLASS_URI,
                EnumSet.of(Access.WRITE, Access.CONTROL)));

        model.close();
    }

    private Model createEmptyModel() {
        Model model = RDF2Go.getModelFactory().createModel();
        model.open();

        new Agent(model, SAMPLE_USERACCOUNT_URI, true);

        return model;
    }

    private Model createFilledModel() {
        Model model = createEmptyModel();
        Agent user = Agent.getInstance(model, SAMPLE_USERACCOUNT_URI);
        Agent user2 = Agent.getInstance(model, SAMPLE_USERACCOUNT2_URI);

        Authorization resAuth = new Authorization(model, model.createBlankNode(), true);
        resAuth.setOwner(user);
        resAuth.setAccessTo(SAMPLE_RESOURCE_URI);
        resAuth.setAgent(new Agent(model, SOCC_BOT_AGENT_URI, false));
        resAuth.addAccessMode(Access.READ.asClass(model));
        resAuth.addAccessMode(Access.WRITE.asClass(model));

        Authorization resAuth2 = new Authorization(model, model.createBlankNode(), true);
        resAuth2.setOwner(user);
        resAuth2.setAccessToClass(new Class(model, SAMPLE_CLASS_URI, false));
        resAuth2.setAgent(new Agent(model, SOCC_BOT_AGENT_URI, false));
        resAuth2.addAccessMode(Access.READ.asClass(model));

        Authorization resAuth3 = new Authorization(model, model.createBlankNode(), true);
        resAuth3.setOwner(user2);
        resAuth3.setAccessToClass(new Class(model, SAMPLE_CLASS_URI, false));
        resAuth3.setAgent(new Agent(model, SOCC_BOT_AGENT_URI, false));
        resAuth3.addAccessMode(Access.READ.asClass(model));

        return model;
    }
}
