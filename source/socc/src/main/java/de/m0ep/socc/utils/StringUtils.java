package de.m0ep.socc.utils;

import org.jsoup.Jsoup;

public class StringUtils {

    public static String trimToEmpty(final String value) {
	if (null == value)
	    return "".trim();

	return value.trim();
    }

    public static String stripHTML(final String value) {
	return Jsoup.parse(value).text();
    }
}
