package com.muhammadali.qrauth.qr_auth_demo.service;

import com.muhammadali.qrauth.qr_auth_demo.model.User;
import com.muhammadali.qrauth.qr_auth_demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        // Generate hash before saving user
        user.generateDataHash();

        return userRepository.save(user);
    }
}