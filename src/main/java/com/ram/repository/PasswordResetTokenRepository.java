package com.ram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ram.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
	PasswordResetToken findByToken(String token);
}
