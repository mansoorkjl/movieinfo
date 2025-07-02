package com.manchu.movieinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> payload) {
        try {
            Review review = reviewService.createReview(payload.get("reviewBody"), payload.get("imdbId"));
            return ResponseEntity.status(HttpStatus.CREATED).body(review);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null); // or return ResponseEntity.badRequest().body(new ErrorDTO(...))
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
