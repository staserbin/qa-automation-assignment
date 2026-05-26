package com.flamingo.qa.api.core.graphql;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class QueryLoader {

    private QueryLoader() {}

    public static String load(String fileName) {
        String path = "graphql/" + fileName;
        try (InputStream is = QueryLoader.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                throw new RuntimeException("GraphQL file not found: " + path);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load GraphQL file: " + path, e);
        }
    }
}