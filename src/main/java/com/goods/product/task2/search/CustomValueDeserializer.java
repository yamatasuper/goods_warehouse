package com.goods.product.task2.search;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class CustomValueDeserializer extends JsonDeserializer<Object> {

  @Override
  public Object deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode node = p.getCodec().readTree(p);

    if (node.isTextual()) {
      return node.asText();
    }

    if (node.isNumber()) {
      return node.asDouble();
    }

    return node;
  }
}
