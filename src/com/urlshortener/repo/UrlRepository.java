package com.urlshortener.repo;

import com.urlshortener.db.Db;
import com.urlshortener.model.UrlMapping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UrlRepository {

    public boolean shortCodeExists(String shortCode) {
        String sql = "SELECT 1 FROM urls WHERE short_code = ? LIMIT 1";
        try (Connection connection = Db.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, shortCode);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check short code", e);
        }
    }

    public void save(String shortCode, String longUrl) {
        String sql = "INSERT INTO urls (short_code, long_url) VALUES (?, ?)";
        try (Connection connection = Db.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, shortCode);
            ps.setString(2, longUrl);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save URL mapping", e);
        }
    }

    public UrlMapping findByShortCode(String shortCode) {
        String sql = """
                SELECT id, short_code, long_url, clicks, created_at
                FROM urls
                WHERE short_code = ?
                """;

        try (Connection connection = Db.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, shortCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UrlMapping(
                            rs.getInt("id"),
                            rs.getString("short_code"),
                            rs.getString("long_url"),
                            rs.getInt("clicks"),
                            rs.getTimestamp("created_at").toLocalDateTime());
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch URL mapping", e);
        }
    }

    public void incrementClicks(String shortCode) {
        String sql = "UPDATE urls SET clicks = clicks + 1 WHERE short_code = ?";
        try (Connection connection = Db.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, shortCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update clicks", e);
        }
    }

    public void printAllMappings() {
        String sql = """
                SELECT id, short_code, long_url, clicks, created_at
                FROM urls
                ORDER BY created_at DESC
                """;

        try (Connection connection = Db.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("----------------------------------------");
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Short Code: " + rs.getString("short_code"));
                System.out.println("Long URL: " + rs.getString("long_url"));
                System.out.println("Clicks: " + rs.getInt("clicks"));
                System.out.println("Created At: " + rs.getTimestamp("created_at").toLocalDateTime());
            }

            if (!found) {
                System.out.println("No records found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list mappings", e);
        }
    }

    public UrlMapping findByLongUrl(String longUrl) {
        String sql = """
                SELECT id, short_code, long_url, clicks, created_at
                FROM urls
                WHERE long_url = ?
                """;

        try (Connection connection = Db.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, longUrl);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UrlMapping(
                            rs.getInt("id"),
                            rs.getString("short_code"),
                            rs.getString("long_url"),
                            rs.getInt("clicks"),
                            rs.getTimestamp("created_at").toLocalDateTime());
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch URL by long URL", e);
        }
    }
}