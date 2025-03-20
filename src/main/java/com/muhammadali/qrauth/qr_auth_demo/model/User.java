package com.muhammadali.qrauth.qr_auth_demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false, unique = true) // Store SHA-256 hash
    private String dataHash;

    // Method to generate SHA-256 hash
    public void generateDataHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String rawData = name + email + role; // Combine user fields
            byte[] hashBytes = digest.digest(rawData.getBytes(StandardCharsets.UTF_8));

            // Convert bytes to hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            this.dataHash = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }
}