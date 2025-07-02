package com.manchu.movieinfo;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;


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
        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
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
}
