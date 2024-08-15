package com.example.review_service.controller;

import com.example.review_service.messaging.ReviewMessageProducer;
import com.example.review_service.model.Review;
import com.example.review_service.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    ReviewService reviewService;
    private ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService,ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer=reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){

        return ResponseEntity.ok(reviewService.getAllReviews(companyId));
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review){
        boolean isReviewSaved=reviewService.addReview(companyId,review);

        if(isReviewSaved){
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity<>("Review added successfully", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Review not saved", HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable Long id){
        Review review=reviewService.getReview(id);
        if(null!=review)
            return new ResponseEntity<>(review,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id){
        boolean deleted=reviewService.deleteReview(id);
        if(deleted){
            return new ResponseEntity<>("Review Deleted Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> UpdateReview(@PathVariable Long id,@RequestBody Review updatedReview){
        boolean updated=reviewService.updateReview(id,updatedReview);
        if(updated){
            return new ResponseEntity<>("Review Updated Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/averageRating")
    public Double getAverageReview(@RequestParam Long companyId){
        List<Review> reviewList=reviewService.getAllReviews(companyId);
        return reviewList.stream().mapToDouble(Review::getRatings).average().orElse(0.0);
    }
}
