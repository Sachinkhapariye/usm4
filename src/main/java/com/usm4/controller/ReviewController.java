package com.usm4.controller;

import com.usm4.entity.AppUser;
import com.usm4.entity.Property;
import com.usm4.entity.Review;
import com.usm4.repository.ReviewRepository;
import com.usm4.repository.PropertyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/reviews")
public class ReviewController {

    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;

    public ReviewController(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping
    public ResponseEntity<String> addReview(
            @AuthenticationPrincipal AppUser appUser,
            @RequestParam long propertyId,
            @RequestBody Review review
            ){
        Optional<Property> byId = propertyRepository.findById(propertyId);
        Property property = byId.get();
        review.setAppUser(appUser);
        review.setProperty(property);
        review.setContent(review.getContent());
        review.setRating(review.getRating());
        Review existReview = reviewRepository.fetchUserReview(property, appUser);
        if (existReview != null){
            return new ResponseEntity<>("Review is already given", HttpStatus.BAD_REQUEST);
        }
        reviewRepository.save(review);
        return new ResponseEntity<>("Review is added", HttpStatus.CREATED);
    }
}
//i build session management