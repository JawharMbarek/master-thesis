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

package de.m0ep.socc.core.connector.facebook;

/**
 * Class that stores some important constants that are used within the Facebook
 * Graph API.
 * 
 * @author Florian Müller
 */
public final class FacebookApiConstants {
    // Connection names
    static final String CONNECTION_COMMENTS = "comments";
    static final String CONNECTION_FEED = "feed";

    // JSON Fields
    static final String FIELD_CAPTION = "caption";
    static final String FIELD_COMMENTS = "comments";
    static final String FIELD_CONNECTIONS = "connections";
    static final String FIELD_CREATED_TIME = "created_time";
    static final String FIELD_DATA = "data";
    static final String FIELD_DESCRIPTION = "description";
    static final String FIELD_FROM = "from";
    static final String FIELD_ID = "id";
    static final String FIELD_LINK = "link";
    static final String FIELD_MESSAGE = "message";
    static final String FIELD_METADATA = "metadata";
    static final String FIELD_NAME = "name";
    static final String FIELD_SOURCE = "source";
    static final String FIELD_STORY = "story";
    static final String FIELD_TYPE = "type";
    static final String FIELD_UPDATED_TIME = "updated_time";

    // Request parameters
    static final String PARAM_LIMIT = "limit";
    static final String PARAM_SINCE = "since";
    static final String PARAM_FIELDS = "fields";

    // Prevent object creation.
    private FacebookApiConstants() {
    }
}
