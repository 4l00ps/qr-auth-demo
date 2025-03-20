package com.muhammadali.qrauth.qr_auth_demo.repository;

import com.muhammadali.qrauth.qr_auth_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); // Prevent duplicate registrations
}