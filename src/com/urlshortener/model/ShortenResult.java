package com.urlshortener.model;

public class ShortenResult {
    private final String shortCode;
    private final boolean alreadyExisted;

    public ShortenResult(String shortCode, boolean alreadyExisted) {
        this.shortCode = shortCode;
        this.alreadyExisted = alreadyExisted;
    }

    public String getShortCode() {
        return shortCode;
    }

    public boolean isAlreadyExisted() {
        return alreadyExisted;
    }
}