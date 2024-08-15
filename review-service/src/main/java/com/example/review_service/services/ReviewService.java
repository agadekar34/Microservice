package com.example.review_service.services;

import com.example.review_service.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews(Long companyId);
    boolean addReview(Long companyId,Review review);
    Review getReview(Long reviewId);
    boolean updateReview(Long reviewId,Review updatedReview);
    boolean deleteReview(Long reviewId);
}
