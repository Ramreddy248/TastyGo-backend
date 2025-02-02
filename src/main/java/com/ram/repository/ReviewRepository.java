package com.ram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ram.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
