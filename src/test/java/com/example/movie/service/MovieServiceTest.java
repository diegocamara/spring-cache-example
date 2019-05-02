package com.example.movie.service;

import com.example.movie.entity.Movie;
import com.example.movie.repository.MovieRepository;
import com.example.movie.service.impl.MovieServiceImpl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class MovieServiceTest {

    @TestConfiguration
    static class MovieServiceImplContextConfiguration {

        @Bean
        public MovieService movieService(MovieRepository movieRepository) {
            return new MovieServiceImpl(movieRepository);
        }

    }

    @Autowired
    private MovieService movieService;

    @MockBean
    private MovieRepository movieRepository;

    @Test
    public void whenValidMovie_thenMovieShouldBeReturned() {

        Movie movie = new Movie("Super Title");

        Movie movieCreated = new Movie(movie.getTitle());
        movieCreated.setId(UUID.randomUUID());

        Mockito.when(movieRepository.save(any(Movie.class))).thenReturn(movieCreated);

        Movie resultMovie = this.movieService.create(movie);

        assertEquals(movieCreated.getId(), resultMovie.getId());

    }

    @Test
    public void whenValidMovieId_thenMovieShouldBeReturned(){

        Optional<Movie> movie = Optional.of(new Movie(UUID.randomUUID(), "Super Title"));

        Mockito.when(movieRepository.findById(movie.get().getId())).thenReturn(movie);

        Movie resultMovie = this.movieService.findById(movie.get().getId());

        Assert.assertEquals(movie.get(), resultMovie);

    }

}
