package org.zsombok.filesystemhandler.utils.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import lombok.extern.slf4j.Slf4j;
import org.zsombok.filesystemhandler.models.Action;

@Slf4j
public class ActionConverter implements AttributeConverter<Action, String> {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(Action attribute) {
    try {
      return objectMapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      log.warn("Error while processing JSON", e);
      return null;
    }
  }

  @Override
  public Action convertToEntityAttribute(String dbData) {
    try {
      return objectMapper.readValue(dbData, Action.class);
    } catch (JsonProcessingException e) {
      log.warn("Error while processing JSON", e);
      return null;
    }
  }
}
