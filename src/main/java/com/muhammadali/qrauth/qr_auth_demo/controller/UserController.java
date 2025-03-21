package com.muhammadali.qrauth.qr_auth_demo.controller;

import com.muhammadali.qrauth.qr_auth_demo.model.User;
import com.muhammadali.qrauth.qr_auth_demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ✅ New Endpoint: Retrieve QR Code for a Specific User
    @GetMapping("/{id}/qrcode")
    public ResponseEntity<?> getUserQRCode(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok().body(user.get().getQrCode());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}