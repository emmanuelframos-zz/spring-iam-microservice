package com.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateDeserializer extends JsonDeserializer<Date> {

    public Date deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException {
        String date = jsonparser.getText();

        try {
            if (date == null)
                return null;

            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(date);
        } catch (ParseException pEx) {
            throw new RuntimeException(pEx);
        }
    }
}