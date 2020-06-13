package com.google.sps.data;

public class Comment {
    private String content;
    private long timestamp;  // System.currentTimeMillis()

    public Comment(String content, long timestamp) {
        this.content = content;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return this.content;
    }
}
