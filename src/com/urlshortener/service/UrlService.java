package com.urlshortener.service;

import com.urlshortener.model.UrlMapping;
import com.urlshortener.repo.UrlRepository;

public class UrlService {
    private final UrlRepository repository;
    private final ShortCodeGenerator generator;

    public UrlService(UrlRepository repository, ShortCodeGenerator generator) {
        this.repository = repository;
        this.generator = generator;
    }

    public String shortenUrl(String longUrl) {
        if (longUrl == null || longUrl.isBlank()) {
            throw new IllegalArgumentException("URL cannot be empty");
        }

        String shortCode;
        int attempts = 0;

        do {
            shortCode = generator.generate(6);
            attempts++;
            if (attempts > 100) {
                throw new RuntimeException("Could not generate a unique short code");
            }
        } while (repository.shortCodeExists(shortCode));

        repository.save(shortCode, longUrl);
        return shortCode;
    }

    public String getLongUrl(String shortCode) {
        UrlMapping mapping = repository.findByShortCode(shortCode);
        if (mapping == null) {
            return null;
        }
        repository.incrementClicks(shortCode);
        return mapping.getLongUrl();
    }

    public void printAll() {
        repository.printAllMappings();
    }
}