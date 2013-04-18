package de.m0ep.socc.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {
    private static final String ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    private static DateFormat dateFormat;

    private DateUtils() {
    }

    public static DateFormat getISO8601Format() {
	if (null == dateFormat) {
	    dateFormat = new SimpleDateFormat(ISO8601_FORMAT);
	}

	return dateFormat;
    }

    public static String formatISO8601(final Date date) {
	return getISO8601Format().format(date);
    }

    public static String formatISO8601(final Long millis) {
	return getISO8601Format().format(new Date(millis));
    }

    public static Date parseISO8601(final String value) throws ParseException {
	return getISO8601Format().parse(value);
    }
}
