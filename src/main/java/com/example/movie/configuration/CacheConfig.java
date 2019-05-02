package com.example.movie.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Autowired
    private Environment environment;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(environment.getProperty("spring.redis.host"), Integer.parseInt(environment.getProperty("spring.redis.port")));
        redisStandaloneConfiguration.setPassword(environment.getProperty("spring.redis.password"));
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(600)).disableCachingNullValues();
    }

    @Bean
    public RedisCacheManager cacheManager() {
        return RedisCacheManager.builder(redisConnectionFactory()).cacheDefaults(cacheConfiguration()).transactionAware().build();
    }

}
