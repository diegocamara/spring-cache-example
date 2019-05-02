package com.example.movie.repository;

import com.example.movie.entity.Movie;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
}
