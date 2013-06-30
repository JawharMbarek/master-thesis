/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller Permission is hereby
 * granted, free of charge, to any person obtaining a copy of this software and
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

package de.m0ep.canvas;

import org.apache.http.client.methods.HttpGet;

import com.damnhandy.uri.template.UriTemplate;

import de.m0ep.canvas.exceptions.CanvasException;
import de.m0ep.canvas.model.Course;

public class Courses extends AbstractEndpoint {
    private static final String PARAM_COURSE_ID = "courseId";

    private static final String PATH = "/courses";
    private static final String PATH_COURSE = "/courses/{courseId}";

    public class List extends CanvasRequest<Course> {
        public List() {
            super(Courses.this.getClient(),
                    HttpGet.class,
                    getEndpoint(),
                    Course.class);
        }

        @Override
        public Course execute() throws CanvasException {
            throw new UnsupportedOperationException(
                    "execute() is not supported by List");
        }
    }

    public class Get extends CanvasRequest<Course> {
        public Get(final long courseId) {
            super(Courses.this.getClient(),
                    HttpGet.class,
                    UriTemplate.fromTemplate(PATH_COURSE)
                            .set(PARAM_COURSE_ID, courseId)
                            .expand(),
                    Course.class);
        }

        @Override
        public Pagination<Course> executePagination()
                throws CanvasException {
            throw new UnsupportedOperationException(
                    "executePagination() is not supported by Get");
        }
    }

    public Courses(final CanvasClient client) {
        setClient(client);
        setEndpoint(PATH);
    }

    public List list() {
        List request = new List();
        initializeRequest(request);
        return request;
    }

    public Get get(final long courseId) {
        Get request = new Get(courseId);
        initializeRequest(request);
        return request;
    }

    public DiscussionTopics discussionTopics(final long courseId) {
        return new DiscussionTopics(
                getClient(),
                UriTemplate.fromTemplate(PATH_COURSE)
                        .set(PARAM_COURSE_ID, courseId)
                        .expand());
    }
}
