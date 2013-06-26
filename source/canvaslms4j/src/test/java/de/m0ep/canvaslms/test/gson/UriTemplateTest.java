package de.m0ep.canvaslms.test.gson;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.m0ep.canvas.utils.UriTemplate;

public class UriTemplateTest {

    @Test
    public void testConstructor() {
	String template = "/test/{someId}/operation/{aValue}";
	int someId = 1337;
	String aValue = "foobar";
	String expected = "/test/1337/operation/foobar";

	String actual = new UriTemplate(template)
		.set("someId", someId)
		.set("aValue", aValue)
		.toString();

	assertEquals(expected, actual);
    }

}
