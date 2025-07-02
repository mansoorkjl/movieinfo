package com.manchu.movieinfo;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MovieServiceTest {
    private final MovieRepository mockRepo = mock(MovieRepository.class);
    private final MovieService movieService = new MovieService(mockRepo);

    @Test
    void shouldReturnAllMoviesFromRepository() {
        MovieRepository mockRepo = mock(MovieRepository.class);

        Movie m = new Movie(
                new ObjectId(),
                "tt007",
                "Jaws",
                "1975",
                "https://youtube.com/jaws",
                "https://image.tmdb.org/poster.jpg",
                List.of("Thriller"),
                List.of("bg.jpg"),
                List.of()
        );

        when(mockRepo.findAll()).thenReturn(List.of(m));
        MovieService service = new MovieService(mockRepo);
        List<Movie> result = service.allMovies();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Jaws");
    }


    @Test
    void Z_zeroMovies_returnsEmptyList() {
        when(mockRepo.findAll()).thenReturn(List.of());
        List<Movie> result = movieService.allMovies();
        assertTrue(result.isEmpty());
    }

    @Test
    void M_manyMovies_returnsAllMovies() {
        List<Movie> movies = List.of(new Movie(), new Movie(), new Movie(), new Movie());
        when(mockRepo.findAll()).thenReturn(movies);
        List<Movie> result = movieService.allMovies();
        assertEquals(4, result.size());
    }

    @Test
    void shouldReturnSingleMovieFromRepository() {
        MovieRepository mockRepo = mock(MovieRepository.class);
        Movie m = new Movie(
                new ObjectId(),
                "tt007",
                "Jaws",
                "1975",
                "https://youtube.com/jaws",
                "https://image.tmdb.org/poster.jpg",
                List.of("Thriller"),
                List.of("bg.jpg"),
                List.of()
        );
        when(mockRepo.findMovieByImdbId("tt007")).thenReturn(Optional.of(m));
        MovieService service = new MovieService(mockRepo);
        Optional<Movie> result = service.singleMovie("tt007");
        assertThat(result).isPresent();
        assertThat(result.get().getImdbId()).isEqualTo("tt007");
       // assertThat(result).
        //assertThat(result.get(0).getTitle()).isEqualTo("Jaws");
    }

    @Test
    void E_nonexistentImdbId_returnsEmptyOptional() {
        when(mockRepo.findMovieByImdbId("tt404")).thenReturn(Optional.empty());
        MovieService service = new MovieService(mockRepo);
        Optional<Movie> result = service.singleMovie("tt404");
        assertThat(movieService.singleMovie("tt404")).isEmpty();
    }
}