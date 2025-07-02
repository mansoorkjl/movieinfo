package com.manchu.movieinfo;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*@WebMvcTest(MovieController.class)
public class MovieControllerTestold {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    void shouldReturnAllMoviesAsJsonArray() throws Exception {
        // Arrange: create mock movies
        Movie m1 = new Movie(new ObjectId(), "tt001", "Matrix", "1999", "link1", "poster1",
                List.of("Action", "Sci-Fi"), List.of("bg1"), List.of("rev1"));
        Movie m2 = new Movie(new ObjectId(), "tt002", "Dune", "2021", "link2", "poster2",
                List.of("Adventure"), List.of("bg2"), List.of("rev2"));

        when(movieService.allMovies()).thenReturn(Arrays.asList(m1, m2));

        // Act & Assert
        mockMvc.perform(get("/api/v1/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Matrix"))
                .andExpect(jsonPath("$[1].title").value("Dune"));
    }

    @Test
    void shouldReturnEmptyArrayIfNoMoviesPresent() throws Exception {
        when(movieService.allMovies()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldReturnNotFoundOnInvalidEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/moviess")) // intentional typo
                .andExpect(status().isNotFound());
    }
} */