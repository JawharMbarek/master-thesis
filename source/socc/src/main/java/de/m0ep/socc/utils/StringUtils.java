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

package de.m0ep.socc.utils;

import org.jsoup.Jsoup;

public class StringUtils {

    /**
     * Trim whitespace character form a string. If value is null, return an
     * empty string.
     * 
     * @param value
     *            String to trim
     * @return Return trimmed or empty string.
     */
    public static String trimToEmpty(final String value) {
	if (null == value)
	    return "";

	return value.trim();
    }

    /**
     * User JSoup to remove all HTML tags from a string
     * 
     * @param value
     *            String to strip
     * @return String without HTML tags
     */
    public static String stripHTML(final String value) {
	return Jsoup.parse(value).text();
    }

    /**
     * Add a slash to the end of the string, if it isn't present.
     * 
     * @param value
     * @return value with "/" at the end
     */
    public static String endsWithSlash(final String value) {
	if (!value.endsWith("/"))
	    return value.concat("/");

	return value;
    }
}
