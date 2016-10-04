package com.ssg.demo.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mvincent on 26/09/2016.
 */
@Component
public class JsonDateSerializer extends JsonSerializer<Date> {

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

  @Override
  public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
    throws IOException, JsonProcessingException {

    String formattedDate = dateFormat.format(date);

    gen.writeString(formattedDate);
  }
}
