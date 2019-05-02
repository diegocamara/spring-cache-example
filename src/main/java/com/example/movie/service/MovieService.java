package com.example.movie.service;

import com.example.movie.entity.Movie;

import java.util.UUID;

public interface MovieService {

    Movie create(Movie movie);

    Movie findById(UUID id);
}
