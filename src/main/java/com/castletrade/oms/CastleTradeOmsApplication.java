package com.castletrade.oms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Castle Trade Order Management System.
 * Optimized for Java 21+ and Spring Boot 3.2+
 */
@SpringBootApplication
public class CastleTradeOmsApplication {

    public static void main(String[] args) {
        // Ensuring the application utilizes Virtual Threads if available (configured in properties)
        SpringApplication.run(CastleTradeOmsApplication.class, args);
    }
}
