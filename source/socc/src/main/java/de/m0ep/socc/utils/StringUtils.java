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
