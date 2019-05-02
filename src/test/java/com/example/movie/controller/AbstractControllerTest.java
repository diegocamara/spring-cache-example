package com.example.movie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    public String asJsonString(Object object){
        try{
            return this.objectMapper.writeValueAsString(object);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public byte[] asBytes(Object object){
        try{
            return this.objectMapper.writeValueAsBytes(object);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

}
