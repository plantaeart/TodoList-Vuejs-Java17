package com.tododo.tododo.utils;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static File jsonFile = new File("src\\main\\resources\\data.json");
    private static File jsonFileTest = new File("src\\test\\java\\com\\tododo\\tododo\\resources\\dataTest.json");

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

    public static File getJsonFile(boolean isTest) {
        return isTest ? jsonFileTest : jsonFile;
    }
}