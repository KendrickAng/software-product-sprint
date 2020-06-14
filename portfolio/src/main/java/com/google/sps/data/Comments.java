package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the comments on a website's webpage.
 */
public class Comments {
    private final List<String> comments = new ArrayList<>();

    public void addComment(String comment) {
        comments.add(comment);
    }
}
