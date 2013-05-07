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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.common.base.Preconditions;

/**
 * Class with utility methods to handle the ISO8601 date format in RDF files.
 * 
 * @author Florian Müller
 * 
 */
public final class DateUtils {
    private static final String ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    private static DateFormat dateFormat;

    /*
     * Private constructor to avoid creating objects from this class.
     */
    private DateUtils() {
    }

    /**
     * Get a {@link DateFormat} to handle the ISO8601 date format.
     * 
     * @return A {@link DateFormat} for ISO8601
     */
    public static DateFormat getISO8601Format() {
	if (null == dateFormat) {
	    dateFormat = new SimpleDateFormat(ISO8601_FORMAT);
	}

	return dateFormat;
    }

    /**
     * Convert a {@link Date} to a ISO8601 string.
     * 
     * @param date
     *            {@link Date} to convert.
     * @return ISO8601 string.
     * 
     * @throws NullPointerException
     *             Thrown if date is null.
     */
    public static String formatISO8601(final Date date) {
	return getISO8601Format().format(
		Preconditions.checkNotNull(date, "Date can not be null."));
    }

    /**
     * Convert a {@link Long} to a ISO8601 string.
     * 
     * @param millis
     *            {@link Long} to convert.
     * @return ISO8601 string.
     * 
     * @throws NullPointerException
     *             Thrown if millis is null.
     */
    public static String formatISO8601(final Long millis) {
	return getISO8601Format().format(
		new Date(Preconditions.checkNotNull(
			millis,
			"Date can not be null.")));
    }

    /**
     * Parse a ISO8601 String to a {@link Date} object.
     * 
     * @param value
     *            ISO8601 String
     * @return {@link Date} object of the ISO8601 String.
     * 
     * @throws ParseException
     *             Thrown if parsing fails.
     * @throws NullPointerException
     *             Thrown if value is null.
     */
    public static Date parseISO8601(final String value) throws ParseException {
	return getISO8601Format().parse(
		Preconditions.checkNotNull(value, "value can not be null."));
    }
}
