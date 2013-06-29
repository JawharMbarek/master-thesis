
package de.m0ep.canvas;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.damnhandy.uri.template.UriTemplate;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.m0ep.canvas.exceptions.CanvasException;
import de.m0ep.canvas.gson.ISO8601TypeAdapter;
import de.m0ep.canvas.model.Course;
import de.m0ep.canvas.model.DiscussionTopic;
import de.m0ep.canvas.model.DiscussionTopicEntry;

public class Canvas {

    private static final String SERVICE_PATH = "/api/v1";

    private static final String COURSE_ID = "courseId";
    private static final String TOPIC_ID = "topicId";
    private static final String ENTRY_ID = "entryId";

    private static final String PATH_COURSES = "/courses";
    private static final String PATH_COURSE_DISCUSSION_TOPICS =
            "/courses/{courseId}/discussion_topics";
    private static final String PATH_COURSE_DISCUSSION_TOPIC_ENTRIES =
            "/courses/{courseId}/discussion_topics/{topicId}/entries";
    private static final String PATH_COURSE_DISCUSSION_TOPIC_ENTRY_REPLIES =
            "/courses/{courseId}/discussion_topics/{topicId}/entries/{entryId}/replies";

    private static final String PATH_COURSE = "/courses/{courseId}";
    private static final String PATH_COURSE_DISCUSSION_TOPIC =
            "/courses/{courseId}/discussion_topics/{topicId}";

    private String oauthToken;
    private HttpClient httpClient;

    private Gson gson;

    private String baseUri;
    private String rootUri;

    /**
     * Constructor to create a new {@link Canvas}
     * 
     * @param uri The uri to a CanvasLMS instance.
     * @param oauthtoken An CanvasLMS accesstoken
     * @throws NullPointerException Thrown if uri or accesstoken are null.
     * @throws IllegalArgumentException Thrown if uri or accesstoken are empty.
     */
    public Canvas(final String uri, final String oauthtoken) {
        this.rootUri = Preconditions.checkNotNull(uri,
                "Required parameter uri must be specified.");

        Preconditions.checkArgument(
                !uri.isEmpty(),
                "Required parameter uri may not be empty");

        if (this.rootUri.endsWith("/")) { // remove tailing backslash
            this.rootUri = this.rootUri.substring(0, this.rootUri.length() - 1);
        }

        this.baseUri = this.rootUri + SERVICE_PATH;

        this.oauthToken = Preconditions.checkNotNull(oauthtoken,
                "Required parameter accesstoken must be specified.");
        Preconditions.checkArgument(
                !oauthtoken.isEmpty(),
                "Required parameter accesstoken may not be empty");

        this.httpClient = new DefaultHttpClient();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Date.class,
                        new ISO8601TypeAdapter().nullSafe())
                .create();

    }

    public String getRootUri() {
        return baseUri;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public String getOAuthToken() {
        return oauthToken;
    }

    public Gson getGson() {
        return gson;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    protected void initialize(CanvasRequest<?> request) {
        request.setOauthToken(oauthToken);
    }

    public Courses courses() {
        return new Courses();
    }

    public class Courses {
        public List list() {
            List result = new List();
            initialize(result);
            return result;
        }

        public class List extends CanvasRequest<Course> {
            public List() {
                super(Canvas.this,
                        HttpGet.class,
                        PATH_COURSES,
                        null,
                        Course.class);
            }

            @Override
            public Course execute() throws CanvasException {
                throw new UnsupportedOperationException(
                        "execute() is not supported by List");
            }
        }

        public Get get(final long id) {
            Get result = new Get(id);
            initialize(result);
            return result;
        }

        public class Get extends CanvasRequest<Course> {
            public Get(final long id) {
                super(Canvas.this,
                        HttpGet.class,
                        UriTemplate.fromTemplate(PATH_COURSE)
                                .set(COURSE_ID, id)
                                .expand(),
                        null,
                        Course.class);
            }

            @Override
            public Pagination<Course> executePagination()
                    throws CanvasException {
                throw new UnsupportedOperationException(
                        "executePagination() is not supported by Get");
            }
        }

        public DiscussionTopics discussionTopics(final long courseId) {
            return new DiscussionTopics(courseId);
        }

        public class DiscussionTopics {
            private long courseId;

            public DiscussionTopics(long courseId) {
                this.courseId = courseId;
            }

            public long getCourseId() {
                return courseId;
            }

            public List list() {
                List result = new List();
                initialize(result);
                return result;
            }

            public class List extends CanvasRequest<DiscussionTopic> {
                public List() {
                    super(Canvas.this,
                            HttpGet.class,
                            UriTemplate.fromTemplate(PATH_COURSE_DISCUSSION_TOPICS)
                                    .set(COURSE_ID, getCourseId())
                                    .expand()
                            , null,
                            DiscussionTopic.class);
                }

                @Override
                public DiscussionTopic execute() throws CanvasException {
                    throw new UnsupportedOperationException(
                            "execute() is not supported by List");
                }
            }

            public Get get(final long topicId) {
                Get result = new Get(topicId);
                initialize(result);
                return result;
            }

            public class Get extends CanvasRequest<DiscussionTopic> {
                public Get(long topicId) {
                    super(Canvas.this,
                            HttpGet.class,
                            UriTemplate.fromTemplate(PATH_COURSE_DISCUSSION_TOPIC)
                                    .set(COURSE_ID, getCourseId())
                                    .set(TOPIC_ID, topicId)
                                    .expand()
                            , null,
                            DiscussionTopic.class);
                }

                @Override
                public Pagination<DiscussionTopic> executePagination()
                        throws CanvasException {
                    throw new UnsupportedOperationException(
                            "executePagination() is not supported by Get");
                }
            }

            public Entries entries(final long topicId) {
                return new Entries(topicId);
            }

            public class Entries {
                private final long topicId;

                public Entries(final long topicId) {
                    this.topicId = topicId;
                }

                public long getTopicId() {
                    return topicId;
                }

                public List list() {
                    List result = new List();
                    initialize(result);
                    return result;
                }

                public class List extends CanvasRequest<DiscussionTopicEntry> {
                    public List() {
                        super(
                                Canvas.this,
                                HttpGet.class,
                                UriTemplate.fromTemplate(PATH_COURSE_DISCUSSION_TOPIC_ENTRIES)
                                        .set(COURSE_ID, getCourseId())
                                        .set(TOPIC_ID, getTopicId())
                                        .expand(),
                                null,
                                DiscussionTopicEntry.class);
                    }

                    @Override
                    public DiscussionTopicEntry execute()
                            throws CanvasException {
                        throw new UnsupportedOperationException(
                                "execute() is not supported by List");
                    }
                }

                public Post post(final String message) {
                    Post result = new Post(message);
                    initialize(result);
                    return result;
                }

                public class Post extends CanvasRequest<DiscussionTopicEntry> {
                    public Post(final String message) {
                        super(
                                Canvas.this,
                                HttpPost.class,
                                UriTemplate.fromTemplate(PATH_COURSE_DISCUSSION_TOPIC_ENTRIES)
                                        .set(COURSE_ID, getCourseId())
                                        .set(TOPIC_ID, topicId)
                                        .expand(),
                                new UrlEncodedFormEntity(
                                        Arrays.asList(
                                                new BasicNameValuePair(
                                                        "message",
                                                        message)),
                                        Charset.defaultCharset()),
                                DiscussionTopicEntry.class);

                    }

                    @Override
                    public Pagination<DiscussionTopicEntry> executePagination()
                            throws CanvasException {
                        throw new UnsupportedOperationException(
                                "executePagination() is not supported by Post");
                    }
                }

                public Replies replies(final long entryId) {
                    return new Replies(entryId);
                }

                public class Replies {
                    private final long entryId;

                    public Replies(final long entryId) {
                        this.entryId = entryId;
                    }

                    public long getEntryId() {
                        return entryId;
                    }

                    public List list() {
                        List result = new List();
                        initialize(result);
                        return result;
                    }

                    public class List extends
                            CanvasRequest<DiscussionTopicEntry> {

                        public List() {
                            super(
                                    Canvas.this,
                                    HttpGet.class,
                                    UriTemplate.fromTemplate(
                                            PATH_COURSE_DISCUSSION_TOPIC_ENTRY_REPLIES)
                                            .set(COURSE_ID, getCourseId())
                                            .set(TOPIC_ID, getTopicId())
                                            .set(ENTRY_ID, entryId)
                                            .expand(),
                                    null,
                                    DiscussionTopicEntry.class);
                        }

                        @Override
                        public DiscussionTopicEntry execute()
                                throws CanvasException {
                            throw new UnsupportedOperationException(
                                    "execute() is not supported by List");
                        }
                    }

                    public Post post(final String message) {
                        Post result = new Post(message);
                        initialize(result);
                        return result;
                    }

                    public class Post extends
                            CanvasRequest<DiscussionTopicEntry> {
                        public Post(final String message) {
                            super(
                                    Canvas.this,
                                    HttpPost.class,
                                    UriTemplate.fromTemplate(
                                            PATH_COURSE_DISCUSSION_TOPIC_ENTRY_REPLIES)
                                            .set(COURSE_ID, getCourseId())
                                            .set(TOPIC_ID, topicId)
                                            .set(ENTRY_ID, entryId)
                                            .expand(),
                                    new UrlEncodedFormEntity(
                                            Arrays.asList(
                                                    new BasicNameValuePair(
                                                            "message",
                                                            message)),
                                            Charset.defaultCharset()),
                                    DiscussionTopicEntry.class);
                        }

                        @Override
                        public Pagination<DiscussionTopicEntry> executePagination()
                                throws CanvasException {
                            throw new UnsupportedOperationException(
                                    "executePagination() is not supported by Post");
                        }
                    }
                }
            }
        }
    }
}
