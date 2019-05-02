package com.example.movie.service.impl;

import com.example.movie.entity.Movie;
import com.example.movie.repository.MovieRepository;
import com.example.movie.service.MovieService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MovieServiceImpl implements MovieService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    @Transactional
    public Movie create(Movie movie) {
        return this.movieRepository.save(movie);
    }

    @Override
    @Cacheable(value = "movies", key = "#id")
    @Transactional
    public Movie findById(UUID id) {
        return this.movieRepository.findById(id).orElse(null);
    }


}
