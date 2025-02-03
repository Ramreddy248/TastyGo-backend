package com.ram.service;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ram.Exception.UserException;
import com.ram.config.JwtProvider;
import com.ram.model.PasswordResetToken;
import com.ram.model.User;
import com.ram.repository.PasswordResetTokenRepository;
import com.ram.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {


	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	@Autowired
	private JavaMailSender javaMailSender;

//	public UserServiceImplementation(
//			UserRepository userRepository,
//			JwtProvider jwtProvider,
//			PasswordEncoder passwordEncoder,
//			PasswordResetTokenRepository passwordResetTokenRepository,
//			JavaMailSender javaMailSender) {
//
//		this.userRepository=userRepository;
//		this.jwtProvider=jwtProvider;
//		this.passwordEncoder=passwordEncoder;
//		this.passwordResetTokenRepository=passwordResetTokenRepository;
//		this.javaMailSender=javaMailSender;
//
//	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email=jwtProvider.getEmailFromJwtToken(jwt);
		
		
		User user = userRepository.findByEmail(email);
		
		if(user==null) {
			throw new UserException("user not exist with email "+email);
		}
//		System.out.println("email user "+user.get().getEmail());
		return user;
	}

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}


	@Override
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

	@Override
	public void sendPasswordResetEmail(User user) {

        String resetToken = generateRandomToken();

        Date expiryDate = calculateExpiryDate();

        PasswordResetToken passwordResetToken = new PasswordResetToken(resetToken,user,expiryDate);
        passwordResetTokenRepository.save(passwordResetToken);

        // Send an email containing the reset link
		System.out.println("http://localhost:5454/auth/reset-password?token=" + resetToken);
        sendEmail(user.getEmail(), "Password Reset", "Click the following link to reset your password: http://localhost:3000/account/reset-password?token=" + resetToken);
	}
	private void sendEmail(String to, String subject, String message) {
	    SimpleMailMessage mailMessage = new SimpleMailMessage();

	    mailMessage.setTo(to);
	    mailMessage.setSubject(subject);
	    mailMessage.setText(message);

	    javaMailSender.send(mailMessage);
	}

	private String generateRandomToken() {

		return UUID.randomUUID().toString();
	}

	private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 10);
        return cal.getTime();
    }
	
	@Override
	public User findUserByEmail(String username) throws UserException {
		
		User user=userRepository.findByEmail(username);
		
		if(user!=null) {
			
			return user;
		}
		
		throw new UserException("user not exist with username "+username);
	}



}
