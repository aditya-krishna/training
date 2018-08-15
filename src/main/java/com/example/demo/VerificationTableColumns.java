package com.example.demo;

public enum VerificationTableColumns {
    DEVICE_ID("\"DEVICE_ID\""),
    VERIFIED("\"VERIFIED\""),
    ATTEMPTS("\"ATTEMPTS\""),
    CREATED_DATE_TIME("\"CREATED_DATE_TIME\""),
    CHANGED_DATE_TIME("\"CHANGED_DATE_TIME\"");

    private final String text;

    VerificationTableColumns(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
