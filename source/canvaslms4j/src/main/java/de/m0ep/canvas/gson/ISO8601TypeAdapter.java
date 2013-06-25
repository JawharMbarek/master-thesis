package de.m0ep.canvas.gson;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class ISO8601TypeAdapter extends TypeAdapter<Date> {

    @Override
    public Date read(JsonReader reader) throws IOException {
	if (JsonToken.NULL == reader.peek()) {
	    reader.nextNull();
	    return null;
	}

	String date = reader.nextString();
	Calendar cal = DatatypeConverter.parseDateTime(date);
	return cal.getTime();
    }

    @Override
    public void write(JsonWriter writer, Date value) throws IOException {
	if (value == null) {
	    writer.nullValue();
	    return;
	}

	Calendar cal = Calendar.getInstance();
	cal.setTime(value);
	String isoString = DatatypeConverter.printDateTime(cal);
	writer.value(isoString);
    }
}
