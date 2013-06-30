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

package de.m0ep.canvas.model;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

public class Attachment {
    @SerializedName("content-type")
    private String contentType;

    private String url;

    private String filename;

    @SerializedName("display_name")
    private String displayName;

    public String getContentType() {
	return contentType;
    }

    public void setContentType(String contentType) {
	this.contentType = contentType;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getFilename() {
	return filename;
    }

    public void setFilename(String filename) {
	this.filename = filename;
    }

    public String getDisplayName() {
	return displayName;
    }

    public void setDisplayName(String displayName) {
	this.displayName = displayName;
    }

    @Override
    public int hashCode() {
	return Objects.hashCode(super.hashCode(), contentType, url, filename,
		displayName);
    }

    @Override
    public boolean equals(Object obj) {
	if (null == obj) {
	    return false;
	}

	if (this == obj) {
	    return true;
	}

	if (this.getClass() != obj.getClass()) {
	    return false;
	}

	Attachment other = (Attachment) obj;

	return Objects.equal(this.url, other.url) &&
		Objects.equal(this.contentType, other.contentType) &&
		Objects.equal(this.filename, other.filename) &&
		Objects.equal(this.displayName, other.displayName);
    }

    @Override
    public String toString() {
	return Objects.toStringHelper(this)
		.add("url", url)
		.add("content_type", contentType)
		.add("filename", filename)
		.add("display_name", displayName)
		.toString();
    }
}