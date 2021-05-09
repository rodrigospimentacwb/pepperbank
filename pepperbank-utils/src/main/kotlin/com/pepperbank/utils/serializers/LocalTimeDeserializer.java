package com.pepperbank.utils.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalTime;

public class LocalTimeDeserializer extends StdDeserializer<LocalTime> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected LocalTimeDeserializer() {
        super(LocalTime.class);
    }

	@Override
    public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return LocalTime.parse(p.getValueAsString());
    }
}
