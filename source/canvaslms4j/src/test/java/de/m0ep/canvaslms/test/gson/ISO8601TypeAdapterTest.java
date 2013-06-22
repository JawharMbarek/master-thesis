package de.m0ep.canvaslms.test.gson;

import java.text.ParseException;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.m0ep.canvaslms.gson.ISO8601TypeAdapter;

public class ISO8601TypeAdapterTest {

    private Gson gson;

    @Before
    public void setUp() throws Exception {
	gson = new GsonBuilder().registerTypeAdapter(
		Date.class,
		new ISO8601TypeAdapter().nullSafe()).create();
    }

    @Test
    public void testString() throws ParseException {
	String testString = "\"2037-07-21T13:29:31Z\"";
	Date testDate = DatatypeConverter.parseDateTime(
		"2037-07-21T13:29:31Z").getTime();

	Date date = gson.fromJson(testString, Date.class);
	Assert.assertEquals(testDate, date);
    }

    @Test
    public void testStringTimeZone() throws ParseException {
	String testString = "\"2037-07-21T13:29:31-06:00\"";
	Date testDate = DatatypeConverter.parseDateTime(
		"2037-07-21T13:29:31-06:00").getTime();

	Date date = gson.fromJson(testString, Date.class);
	Assert.assertEquals(testDate, date);
    }

}
