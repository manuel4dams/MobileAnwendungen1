package com.adams.learningnames.data.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author Manuel Adams
 * @since 2019-01-09
 */
public class JsonHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String content, Class<T> clazz) {
        try {
            return objectMapper.readValue(content, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
