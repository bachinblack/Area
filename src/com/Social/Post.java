package com.Social;

public class Post {
    private String name;
    private String message;

    public Post() {
        name = "";
        message = "";
    }

    public String getName() { return name; }

    public String getMessage() { return message; }

    public void setPost(String newN, String newM) {
        name = newN;
        message = newM;
    }
}
