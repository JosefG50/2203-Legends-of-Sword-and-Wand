package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpgApplication {
    public static void main(String[] args) {
        // 1. Initialize the database table before the web server starts
        DatabaseManager.createNewTable();
        
        // 2. Start the Spring Boot Web Server
        SpringApplication.run(RpgApplication.class, args);
        
        System.out.println("--- Sword and Wand Server is LIVE on port 8080 ---");
    }
}