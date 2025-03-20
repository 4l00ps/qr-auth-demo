package com.muhammadali.qrauth.qr_auth_demo.service;

import com.muhammadali.qrauth.qr_auth_demo.model.User;
import com.muhammadali.qrauth.qr_auth_demo.repository.UserRepository;
import com.muhammadali.qrauth.qr_auth_demo.util.QRCodeGenerator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        user.generateDataHash(); // Generate data hash

        try {
            // Generate QR code with user ID and hashed data
            String qrCodeBase64 = QRCodeGenerator.generateBase64QRCode(user.getId() + "|" + user.getDataHash(), 250, 250);
            user.setQrCode(qrCodeBase64);
        } catch (Exception e) {
            throw new RuntimeException("Error generating QR Code", e);
        }

        return userRepository.save(user);
    }
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}