package com.example.movie.controller;

import com.example.movie.entity.Movie;
import com.example.movie.service.MovieService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie create(@RequestBody Movie movie) {
        movie.setId(UUID.randomUUID());
        return this.movieService.create(movie);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Movie findById(@PathVariable("id") UUID movieId) {
        return this.movieService.findById(movieId);
    }

}
