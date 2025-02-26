package com.ram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ram.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String username);

}
