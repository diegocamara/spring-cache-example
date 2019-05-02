package com.example.movie.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import redis.embedded.RedisServer;

@Component
public class EmbeddedRedis {

    private RedisServer redisServer;

    public EmbeddedRedis(@Value("${spring.redis.port}") int redisPort) throws IOException {
        this.redisServer = new RedisServer(redisPort);
    }

    @PostConstruct
    public void startRedis() {
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        redisServer.stop();
    }

}
