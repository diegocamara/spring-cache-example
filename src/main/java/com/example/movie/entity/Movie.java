package com.example.movie.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Movie implements Serializable {
    @Id
    private UUID id;
    private String title;

    public Movie(){

    }

    public Movie(String title) {
        this.title = title;
    }

    public Movie(UUID id, String title) {
        this.id = id;
        this.title = title;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
