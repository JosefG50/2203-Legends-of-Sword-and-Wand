package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpgApplication {
    public static void main(String[] num) {
        DatabaseManager.createNewTable();
        SpringApplication.run(RpgApplication.class, num);
        System.out.println("--- Sword and Wand Server is LIVE on port 8080 ---");
    }
}