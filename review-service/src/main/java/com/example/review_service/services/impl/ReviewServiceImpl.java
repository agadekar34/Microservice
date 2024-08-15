package com.example.review_service.services.impl;

import com.example.review_service.model.Review;
import com.example.review_service.repository.ReviewRepository;
import com.example.review_service.services.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews=reviewRepository.findByCompanyId(companyId);

        return reviews;
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
        if(companyId!=null && review!=null){
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review getReview( Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean updateReview( Long reviewId, Review updatedReview) {
        Review review=reviewRepository.findById(reviewId).orElse(null);

        if(review!=null){
            review.setCompanyId(updatedReview.getCompanyId());
            review.setDescription(updatedReview.getDescription());
            review.setTitle(updatedReview.getTitle());
            review.setRatings(updatedReview.getRatings());
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview( Long reviewId) {
        Review review=reviewRepository.findById(reviewId).orElse(null);
        if(null!=review){
            reviewRepository.deleteById(reviewId);
            return true;
        }

        return false;
    }
}
