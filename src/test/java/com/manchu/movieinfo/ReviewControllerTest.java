package com.manchu.movieinfo;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.HashMap;
import java.util.stream.Stream;

//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void S_simplePayload_createsReviewAndReturns201() throws Exception {
        Review mockReview = new Review(new ObjectId(), "Black Adam is good");
        Mockito.when(reviewService.createReview("Black Adam is bad", "tt6443346"))
                .thenReturn(mockReview);

        Map<String, String> payload = Map.of(
                "reviewBody", "Black Adam is bad",
                "imdbId", "tt6443346"
        );

        mockMvc.perform(post("/api/v1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.body").value("Black Adam is good"));

    }

    @Test
    void Z_zeroPayload_returnsBadRequest() throws Exception {
        Mockito.when(reviewService.createReview(null, null))
                        .thenThrow(new IllegalArgumentException("Review Body must not be blank"));
        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }


    @ParameterizedTest // Testing parameterized test by sending stream of invalid payload body
    @MethodSource("invalidPayloads")
    void Z_partialPayloads_returnBadRequest(String reviewBody, String imdbId) throws Exception {
        Mockito.when(reviewService.createReview(reviewBody, imdbId))
                .thenThrow(new IllegalArgumentException("Missing fields"));

        Map<String, String> payload = new HashMap<>();
        if (reviewBody != null) payload.put("reviewBody", reviewBody);
        if (imdbId != null) payload.put("imdbId", imdbId);

        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void E_exceptionInService_returns500() throws Exception {
        Mockito.when(reviewService.createReview("bad", "tt000"))
                .thenThrow(new RuntimeException("Service failure"));

        Map<String, String> payload = Map.of(
                "reviewBody", "bad",
                "imdbId", "tt000"
        );

        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isInternalServerError());
    }

    private static Stream<Arguments> invalidPayloads() {
        return Stream.of(
                Arguments.of(null, "tt1234567"),     // Missing reviewBody
                Arguments.of("Great movie!", null),  // Missing imdbId
                Arguments.of(null, null)             // Both missing
        );
    }
}
