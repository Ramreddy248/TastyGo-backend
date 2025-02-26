package com.ram.service;

import java.util.List;

import com.ram.Exception.UserException;
import com.ram.model.User;

public interface UserService {

	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public User findUserByEmail(String email) throws UserException;

	public List<User> findAllUsers();

	void updatePassword(User user, String newPassword);

	void sendPasswordResetEmail(User user);

}
