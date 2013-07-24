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

package de.m0ep.socc.core.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import com.google.common.base.Preconditions;

/**
 * Class with utility methods to handle the ISO8601 date format in RDF files.
 * 
 * @author Florian Müller
 */
public final class DateUtils {
    /**
     * Private constructor, because this class has only static methods.
     */
    private DateUtils() {
    }

    /**
     * Convert a {@link Date} to a ISO8601 string.
     * 
     * @param date
     *            {@link Date} to convert.
     * @return ISO8601 string.
     * @throws NullPointerException
     *             Thrown if date is null.
     */
    public static String formatISO8601(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(
                Preconditions.checkNotNull(
                        date,
                        "Date can not be null."));

        return DatatypeConverter.printDateTime(cal);
    }

    /**
     * Convert a {@link Long} to a ISO8601 string.
     * 
     * @param millis
     *            {@link Long} to convert.
     * @return ISO8601 string.
     * @throws NullPointerException
     *             Thrown if millis is null.
     */
    public static String formatISO8601(final Long millis) {
        return formatISO8601(new Date(
                Preconditions.checkNotNull(
                        millis,
                        "Millis can not be null.")));
    }

    /**
     * Parse a ISO8601 String to a {@link Date} object.
     * 
     * @param value
     *            ISO8601 String
     * @return {@link Date} object of the ISO8601 String.
     * @throws IllegalArgumentException
     *             Thrown if parsing fails.
     * @throws NullPointerException
     *             Thrown if value is null.
     */
    public static Date parseISO8601(final String value) throws ParseException {
        return DatatypeConverter.parseDateTime(
                Preconditions.checkNotNull(
                        value,
                        "Value can not be null.")).getTime();
    }
}
