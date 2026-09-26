package com.collegelibrary.auth;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> details) {
        String username = details.getOrDefault("username", "");
        String password = details.getOrDefault("password", "");
        boolean student = username.equals("student") && password.equals("student123");
        boolean admin = username.equals("admin") && password.equals("admin123");
        if (!student && !admin) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        return Map.of("username", username, "role", admin ? "ADMIN" : "STUDENT");
    }
}
