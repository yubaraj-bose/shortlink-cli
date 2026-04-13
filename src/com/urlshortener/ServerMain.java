package com.urlshortener;

import com.urlshortener.server.RedirectServer;

public class ServerMain {
    public static void main(String[] args) {
        try {
            new RedirectServer(8080).start();
            Thread.currentThread().join();
        } catch (Exception e) {
            throw new RuntimeException("Failed to start redirect server", e);
        }
    }
}