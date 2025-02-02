package com.ram.service;

import java.util.List;

import com.ram.Exception.ReviewException;
import com.ram.model.Review;
import com.ram.model.User;
import com.ram.request.ReviewRequest;

public interface ReviewSerive {
	
    public Review submitReview(ReviewRequest review,User user);
    public void deleteReview(Long reviewId) throws ReviewException;
    public double calculateAverageRating(List<Review> reviews);
}
