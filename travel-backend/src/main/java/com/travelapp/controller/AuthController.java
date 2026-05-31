package com.travelapp.controller;

import com.travelapp.dto.LoginRequest;
import com.travelapp.dto.LoginResponse;
import com.travelapp.dto.RegisterRequest;
import com.travelapp.dto.UserDto;
import com.travelapp.entity.User;
import com.travelapp.service.JwtService;
import com.travelapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userService.findByUsername(request.getUsername());
        
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOpt.get();
        if (!userService.validatePassword(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid password");
        }

        String token = jwtService.generateToken(user.getId(), user.getUsername());
        UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getEmail());

        LoginResponse response = new LoginResponse(token, userDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.createUser(request.getUsername(), request.getPassword(), request.getEmail());
            UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getEmail());
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }
}
