package com.manchu.movieinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(){
        return new ResponseEntity<List<Movie>>(movieService.allMovies(),HttpStatus.OK);
    }

    @GetMapping("/{imdbId}")
    public ResponseEntity<Optional<Movie>> getSingleMovie(@PathVariable String imdbId){
        return new ResponseEntity<Optional<Movie>>(movieService.singleMovie(imdbId),HttpStatus.OK);
    }

//    @GetMapping("/debug")
//    public ResponseEntity<String> debug() {
//        List<Movie> movies = movieService.allMovies();
//
//        if (movies.isEmpty()) {
//            return ResponseEntity.ok("No movies found in the database.");
//        }
//
//        Movie sample = movies.get(0);
//        String output = "Title: " + sample.getTitle() +
//                ", imdbId: " + sample.getImdbId() +
//                ", Poster: " + sample.getPoster();
//
//        return ResponseEntity.ok(output);
//    }

}
