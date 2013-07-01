
package de.m0ep.canvas;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.damnhandy.uri.template.UriTemplate;

import de.m0ep.canvas.exceptions.CanvasException;
import de.m0ep.canvas.model.Entry;

public class Entries extends AbstractEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(Entries.class);

    private static final String PARAM_ENTRY_ID = "entryId";

    private static final String PATH = "/entries";
    private static final String PATH_REPLIES = "/entries/{entryId}/replies";

    public class List extends CanvasRequest<Entry> {
        public List() {
            super(Entries.this.getClient(),
                    HttpGet.class,
                    getEndpoint(),
                    Entry.class);
        }

        @Override
        public Entry execute()
                throws CanvasException {
            throw new UnsupportedOperationException(
                    "execute() is not supported by List");
        }
    }

    public class Post extends CanvasRequest<Entry> {
        public Post(final String message) {
            super(Entries.this.getClient(),
                    HttpPost.class,
                    getEndpoint(),
                    Entry.class);

            setContent(new UrlEncodedFormEntity(
                    Arrays.asList(
                            new BasicNameValuePair(
                                    "message",
                                    message)),
                    Charset.defaultCharset()));
        }

        @Override
        public Pagination<Entry> executePagination()
                throws CanvasException {
            throw new UnsupportedOperationException(
                    "executePagination() is not supported by Post");
        }
    }

    public class ListReplies extends
            CanvasRequest<Entry> {
        public ListReplies(final long entryId) {
            super(Entries.this.getClient(),
                    HttpGet.class,
                    UriTemplate.fromTemplate(getParentEndpoint() + PATH_REPLIES)
                            .set(PARAM_ENTRY_ID, entryId).expand(),
                    Entry.class);
        }

        @Override
        public Entry execute()
                throws CanvasException {
            throw new UnsupportedOperationException(
                    "execute() is not supported by List");
        }
    }

    public class PostReply extends
            CanvasRequest<Entry> {
        public PostReply(final String message, final long entryId) {
            super(Entries.this.getClient(),
                    HttpPost.class,
                    UriTemplate.fromTemplate(getParentEndpoint() + PATH_REPLIES)
                            .set(PARAM_ENTRY_ID, entryId).expand(),
                    Entry.class);

            setContent(new UrlEncodedFormEntity(
                    Arrays.asList(
                            new BasicNameValuePair(
                                    "message",
                                    message)),
                    Charset.defaultCharset()));
        }

        @Override
        public Pagination<Entry> executePagination()
                throws CanvasException {
            throw new UnsupportedOperationException(
                    "executePagination() is not supported by Post");
        }
    }

    public Entries(final CanvasClient client, final String parentEndpointPath) {
        setClient(client);
        setParentEndpoint(parentEndpointPath);
        setEndpoint(getParentEndpoint() + PATH);

        LOG.info("Create Entries for '{}' endpoint.", getParentEndpoint());
    }

    public List list() {
        List request = new List();
        initializeRequest(request);
        return request;
    }

    public Post post(final String message) {
        Post request = new Post(message);
        initializeRequest(request);
        return request;
    }

    public ListReplies listReplies(final long entryId) {
        ListReplies request = new ListReplies(entryId);
        initializeRequest(request);
        return request;
    }

    public PostReply postReply(final String message, final long entryId) {
        PostReply request = new PostReply(message, entryId);
        initializeRequest(request);
        return request;
    }
}
