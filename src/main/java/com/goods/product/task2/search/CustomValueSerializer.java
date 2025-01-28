package com.goods.product.task2.search;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CustomValueSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value instanceof String) {
            gen.writeString((String) value);
        } else if (value instanceof Number) {
            gen.writeNumber(value.toString());
        } else {
            gen.writeObject(value);
        }
    }
}
