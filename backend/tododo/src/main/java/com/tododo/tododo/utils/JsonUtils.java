package com.tododo.tododo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts any object to its JSON representation.
     *
     * @param object The object to convert to JSON.
     * @param <T>    The type of the object.
     * @return A JSON string representing the object.
     * @throws RuntimeException if the conversion fails.
     */
    public static <T> String toJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to JSON: " + e.getMessage(), e);
        }
    }
}