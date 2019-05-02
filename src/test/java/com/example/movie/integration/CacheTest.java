package com.example.movie.integration;

import com.example.movie.entity.Movie;
import com.example.movie.repository.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-integrationtest.properties")
public class CacheTest {

    @TestConfiguration
    static class CacheTestConfiguration {

        @Primary
        @Bean("movieRepositoryMock")
        public MovieRepository movieRepository(MovieRepository movieRepository){
            return Mockito.mock(MovieRepository.class, AdditionalAnswers.delegatesTo(movieRepository));
        }

    }

    private final String MOVIE_PATH = "/movie";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void givenOneRegisteredMovie_whenGetMovieTwice_thenHitMovieRepositoryOnce() throws Exception {

        Movie movie = new Movie("Super Movie");

        ResultActions resultActions = this.mockMvc.perform(post(MOVIE_PATH).contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(movie)));

        MvcResult mvcResult = resultActions.andReturn();

        Movie resultMovie = this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Movie.class);

        String resultMovieUrl = MOVIE_PATH + "/" + resultMovie.getId().toString();

        this.mockMvc.perform(get(resultMovieUrl));
        this.mockMvc.perform(get(resultMovieUrl));

        Mockito.verify(movieRepository, Mockito.times(1)).findById(resultMovie.getId());

    }

}
