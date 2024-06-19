package com.example.demo.service;

import com.example.demo.model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {
    Movie getMovie(Long id);
    List<Movie> getMovies();
    Movie createMovie(Movie movie);
    Movie updateMovie(Movie movie);
    Movie deleteMovie(Long id);
}
