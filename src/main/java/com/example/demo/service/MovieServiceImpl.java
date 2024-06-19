package com.example.demo.service;

import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie getMovie(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    @Override
    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(Movie movie) {
        Optional<Movie> movieDB = movieRepository.findById(movie.getId());
        if(movieDB.isPresent()) {
            if(movie.getName()==null){
                movie.setName(movieDB.get().getName());
            }
            if(movie.getDescription()==null){
                movie.setDescription(movieDB.get().getDescription());
            }
            if(movie.getUserId()==null){
                movie.setUserId(movieDB.get().getUserId());
            }
            return movieRepository.save(movie);
        }else{
            return null;
        }
    }

    @Override
    public Movie deleteMovie(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if(movie.isPresent()) {
            movieRepository.delete(movie.get());
            return movie.get();
        }else{
            return null;
        }
    }
}
