package com.urlshortener;

import com.urlshortener.db.Db;
import com.urlshortener.repo.UrlRepository;
import com.urlshortener.service.ShortCodeGenerator;
import com.urlshortener.service.UrlService;
import com.urlshortener.util.InputUtil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Db.initializeDatabase();

        UrlRepository repository = new UrlRepository();
        ShortCodeGenerator generator = new ShortCodeGenerator();
        UrlService service = new UrlService(repository, generator);

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println();
                System.out.println("===== URL SHORTENER =====");
                System.out.println("1. Shorten URL");
                System.out.println("2. Retrieve Long URL");
                System.out.println("3. Show All URLs");
                System.out.println("4. Exit");

                int choice = InputUtil.readInt(scanner, "Enter choice: ");

                switch (choice) {
                    case 1 -> {
                        String longUrl = InputUtil.readString(scanner, "Enter long URL: ");
                        try {
                            var result = service.shortenUrl(longUrl);
                            if (result.isAlreadyExisted()) {
                                System.out.println("Shortened URL already exists.");
                            } else {
                                System.out.println("URL shortened successfully.");
                            }
                            System.out.println("Short Code: " + result.getShortCode());
                            System.out.println("Browser URL: http://localhost:8080/" + result.getShortCode());
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 2 -> {
                        String shortCode = InputUtil.readString(scanner, "Enter short code: ");
                        try {
                            String longUrl = service.getLongUrl(shortCode);
                            if (longUrl == null) {
                                System.out.println("No URL found for this code.");
                            } else {
                                System.out.println("Original URL: " + longUrl);
                            }
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 3 -> {
                        try {
                            service.printAll();
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 4 -> {
                        System.out.println("Goodbye.");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }
        }
    }
}