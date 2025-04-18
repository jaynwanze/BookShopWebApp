package com.example.bookshop.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class URLQueryHelper {

    // Singleton instance of URLQueryHelper
    private static URLQueryHelper instance = null;

    protected URLQueryHelper() {
        // Prevent instantiation
    }

    public static URLQueryHelper getInstance() {
        if (instance == null) {
            instance = new URLQueryHelper();
        }
        return instance;
    }

    public String buildBookSearchQuery(String title, String author,
            String publisher, String category,
            String sortField, String sortDir) {

        StringBuilder qs = new StringBuilder("?");
        appendIfPresent(qs, "title", title);
        appendIfPresent(qs, "author", author);
        appendIfPresent(qs, "publisher", publisher);
        appendIfPresent(qs, "category", category);

        /* sort is always present -------------------------------------- */
        qs.append("sortField=").append(sortField)
                .append("&sortDir=").append(sortDir);

        return qs.toString();
    }

    private void appendIfPresent(StringBuilder qs, String key, String value) {
        if (value != null && !value.isBlank()) {
            qs.append(key).append('=')
                    .append(URLEncoder.encode(value, StandardCharsets.UTF_8))
                    .append('&');
        }
    }
}