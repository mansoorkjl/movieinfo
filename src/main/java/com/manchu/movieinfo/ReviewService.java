package com.manchu.movieinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import com.manchu.movieinfo.Review;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @CacheEvict(value = "movies", key = "#imdbId")
    public Review createReview(String reviewBody, String imdbId) {
       if (reviewBody == null || reviewBody.trim().isEmpty()  ) {
           throw new IllegalArgumentException("Review Body must not be blank");
       }

       if (imdbId == null || imdbId.trim().isEmpty() ) {
           throw new IllegalArgumentException("imdbIB value must not be empty");
       }

       Review review = reviewRepository.insert(new Review(reviewBody));

       mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review))
                .first();
        return review;
    }
}
