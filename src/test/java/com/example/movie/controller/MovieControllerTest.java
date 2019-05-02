package com.example.movie.controller;

import com.example.movie.entity.Movie;
import com.example.movie.service.MovieService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
public class MovieControllerTest extends AbstractControllerTest {

    private final String MOVIE_PATH = "/movie";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    public void whenCreateMovie_thenReturnCode201() throws Exception {

        Movie movie = new Movie("Super Movie");

        Movie movieCreated = new Movie(movie.getTitle());
        movieCreated.setId(UUID.randomUUID());

        Mockito.when(movieService.create(any(Movie.class))).thenReturn(movieCreated);

        this.mockMvc.perform(post(MOVIE_PATH).contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie))).andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(movieCreated.getId().toString())));

    }

    @Test
    public void whenGetMovieById_thenReturnMovie() throws Exception {

        Movie movie = new Movie(UUID.randomUUID(), "Super Movie");

        String resultUrl = MOVIE_PATH.concat("/" + movie.getId());

        Mockito.when(movieService.findById(movie.getId())).thenReturn(movie);

        this.mockMvc.perform(get(resultUrl)).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(movie.getId().toString())));

    }


}
