package com.ram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ram.model.PasswordResetToken;
import com.ram.repository.PasswordResetTokenRepository;
@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Override
	public PasswordResetToken findByToken(String token) {
		PasswordResetToken passwordResetToken =passwordResetTokenRepository.findByToken(token);
		return passwordResetToken;
	}

	@Override
	public void delete(PasswordResetToken resetToken) {
		passwordResetTokenRepository.delete(resetToken);
		
	}

}
