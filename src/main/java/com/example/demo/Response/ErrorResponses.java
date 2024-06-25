package com.example.demo.Response;

public class ErrorResponses {
    private String description;

    public ErrorResponses(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}