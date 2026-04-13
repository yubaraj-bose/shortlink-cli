package com.urlshortener.model;

import java.time.LocalDateTime;

public class UrlMapping {
    private final int id;
    private final String shortCode;
    private final String longUrl;
    private final int clicks;
    private final LocalDateTime createdAt;

    public UrlMapping(int id, String shortCode, String longUrl, int clicks, LocalDateTime createdAt) {
        this.id = id;
        this.shortCode = shortCode;
        this.longUrl = longUrl;
        this.clicks = clicks;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public int getClicks() {
        return clicks;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}