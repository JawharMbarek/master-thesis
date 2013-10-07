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

package de.m0ep.canvas.gson;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Typeadapter for GSON to convert the Canvas date format to a Java {@link Date}
 * object.
 * 
 * @author Florian Müller
 * 
 */
public class ISO8601TypeAdapter extends TypeAdapter<Date> {

	@Override
	public Date read( JsonReader reader ) throws IOException {
		if ( JsonToken.NULL == reader.peek() ) {
			reader.nextNull();
			return null;
		}

		String date = reader.nextString();
		Calendar cal = DatatypeConverter.parseDateTime( date );
		return cal.getTime();
	}

	@Override
	public void write( JsonWriter writer, Date value ) throws IOException {
		if ( value == null ) {
			writer.nullValue();
			return;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime( value );
		String isoString = DatatypeConverter.printDateTime( cal );
		writer.value( isoString );
	}
}
