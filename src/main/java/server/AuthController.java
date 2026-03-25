package server;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (!DatabaseManager.userExists(username)) {
            return ResponseEntity.status(404).body("Error: That username isn't in the database.");
        }

        if (DatabaseManager.checkPassword(username, password)) {
            return ResponseEntity.ok("Login Successful!");
        } else {
            return ResponseEntity.status(401).body("Error: Wrong password.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> details) {
        String username = details.get("username");
        String password = details.get("password");

        if (DatabaseManager.userExists(username)) {
            return ResponseEntity.status(409).body("Error: That username already exists.");
        }

        DatabaseManager.registerPlayer(username, password);
        return ResponseEntity.ok("User created successfully!");
    }
}