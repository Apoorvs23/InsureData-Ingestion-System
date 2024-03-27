package com.plum.demo.jedis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
//Jedis Java (Client) -> Redis
@Configuration
public class RedisConfig {
    @Value("${redis.port}")
    private int redisPort;

    @Value("${redis.host.url}")
    private String redisHostUrl;

    @Bean
    public JedisPool jedisPool() {
        return new JedisPool(redisHostUrl, redisPort);
    }
}
