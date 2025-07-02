package com.manchu.movieinfo;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.springframework.data.mongodb.core.ExecutableUpdateOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.data.mongodb.core.ExecutableUpdateOperation.*;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class ReviewServiceTest {

    private ReviewRepository reviewRepository;
    private MongoTemplate mongoTemplate;
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        reviewRepository = mock(ReviewRepository.class);
        mongoTemplate = mock(MongoTemplate.class);
        reviewService = new ReviewService();
        reviewService.reviewRepository = reviewRepository;
        reviewService.mongoTemplate = mongoTemplate;
    }

//    @Test
//    void S_simpleReviewCreation_returnsInsertedReview() {
//        Review mockReview = new Review(new ObjectId(), "Black Adam is good");
//        when(reviewRepository.insert(any(Review.class))).thenReturn(mockReview);
//
//        Review result = reviewService.createReview("Black Adam is good", "tt6443346");
//
//        assertThat(result).isNotNull();
//        assertThat(result.getBody()).isEqualTo("Black Adam is good");
//        verify(reviewRepository).insert(any(Review.class));
//    }

    @Test
    void shouldInsertReviewWhenValidPayloadProvided() {
        // Arrange
        String body = "Excellent movie!";
        String imdbId = "tt1234567";
        Review expectedReview = new Review(body);

        // Mock reviewRepository.insert(...)
        when(reviewRepository.insert(any(Review.class))).thenReturn(expectedReview);

        // Mock MongoTemplate fluent update chain
        ExecutableUpdate<Movie> update = mock(ExecutableUpdate.class);
        UpdateWithUpdate updateWithUpdate = mock(UpdateWithUpdate.class);
        TerminatingUpdate terminatingUpdate = mock(TerminatingUpdate.class);
        when(mongoTemplate.update(eq(Movie.class))).thenReturn(update);
        when(update.matching(any(CriteriaDefinition.class))).thenReturn(updateWithUpdate);
        when(updateWithUpdate.apply(any())).thenReturn(terminatingUpdate);
        when(terminatingUpdate.first()).thenReturn(null);


// Mock the chain if needed

        // Act
        Review result = reviewService.createReview(body, imdbId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getBody()).isEqualTo(body);
        verify(reviewRepository).insert(any(Review.class));
    }

}
