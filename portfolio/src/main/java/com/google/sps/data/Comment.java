package com.google.sps.data;

public class Comment {
    private String name;
    private String content;
    private long timestamp;  // System.currentTimeMillis()

    public Comment(String name, String content, long timestamp) {
        this.name = name;
        this.content = content;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return this.content;
    }
}
