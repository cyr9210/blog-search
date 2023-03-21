package com.bong.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URL;

public class ConvertJsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    public static <T> T convertJsonResource(String resourceName, Class<T> valueType) throws IOException {
        String path = String.format("/response/%s.json", resourceName);
        URL resource = ConvertJsonUtil.class.getResource(path);
        return objectMapper.readValue(resource, valueType);
    }
}
