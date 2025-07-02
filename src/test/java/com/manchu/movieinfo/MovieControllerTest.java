package com.manchu.movieinfo;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    void shouldReturnAllMoviesAsJsonArray() throws Exception {
        Movie m1 = new Movie(new ObjectId("68615a9b44c2f72a2b3eef5b"), "tt3915174", "Puss in Boots: The Last Wish", "2022-12-21", "https://www.youtube.com/watch?v=tHb7WlgyaUc",
                "https://image.tmdb.org/t/p/w500/1NqwE6LP9IEdOZ57NCT51ftHtWT.jpg", List.of("Animation", "Comedy"), List.of("bg1", "bg2"), List.of());

        Movie m2 = new Movie(new ObjectId("68615a9b44c2f72a2b3eef5c"), "tt1375666", "Inception", "2010-07-16", "https://www.youtube.com/watch?v=YoHD9XEInc0",
                "https://image.tmdb.org/t/p/w500/inception.jpg", List.of("Sci-Fi", "Thriller"), List.of("bg3"), List.of());

        when(movieService.allMovies()).thenReturn(Arrays.asList(m1, m2));

        mockMvc.perform(get("/api/v1/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Puss in Boots: The Last Wish"))
                .andExpect(jsonPath("$[1].title").value("Inception"));
    }

    @Test
    void shouldReturnEmptyJsonArrayWhenNoMoviesExist() throws Exception {
        when(movieService.allMovies()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldReturn404WhenEndpointIsIncorrect() throws Exception {
        mockMvc.perform(get("/api/v1/moviess"))
                .andExpect(status().isNotFound());
    }
}