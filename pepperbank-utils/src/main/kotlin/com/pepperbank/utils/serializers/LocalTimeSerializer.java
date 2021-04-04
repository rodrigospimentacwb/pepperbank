package com.pepperbank.utils.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeSerializer extends StdSerializer<LocalTime> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LocalTimeSerializer() {
        super(LocalTime.class, false);
    }

    @Override
    public void serialize(LocalTime value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeString(value.format(DateTimeFormatter.ISO_LOCAL_TIME));
    }
}
