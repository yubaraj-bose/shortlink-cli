package com.urlshortener.server;

import com.urlshortener.db.Db;
import com.urlshortener.repo.UrlRepository;
import com.urlshortener.service.ShortCodeGenerator;
import com.urlshortener.service.UrlService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class RedirectServer {
    private final int port;
    private final UrlService service;

    public RedirectServer(int port) {
        this.port = port;
        this.service = new UrlService(new UrlRepository(), new ShortCodeGenerator());
    }

    public void start() throws IOException {
        Db.initializeDatabase();

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", this::handleRequest);
        server.setExecutor(null);
        server.start();

        System.out.println("Redirect server running at http://localhost:" + port);
        System.out.println("Use format: http://localhost:" + port + "/<shortCode>");
    }

    private void handleRequest(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        URI uri = exchange.getRequestURI();
        String path = uri.getPath();

        if (!"GET".equalsIgnoreCase(method)) {
            sendText(exchange, 405, "Method Not Allowed");
            return;
        }

        if (path == null || path.equals("/") || path.isBlank()) {
            sendText(exchange, 200, "Use /<shortCode> in the browser, for example /abc123");
            return;
        }

        String shortCode = path.replaceFirst("^/+", "").replaceFirst("/+$", "");

        String longUrl = service.getLongUrl(shortCode);
        if (longUrl == null) {
            sendText(exchange, 404, "Short code not found");
            return;
        }

        exchange.getResponseHeaders().add("Location", longUrl);
        exchange.sendResponseHeaders(302, -1);
        exchange.close();
    }

    private void sendText(HttpExchange exchange, int statusCode, String message) throws IOException {
        byte[] responseBytes = message.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}