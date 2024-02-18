package com.finance.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author: Lilopop
 * @description:
 */
@Configuration
public class RedisConfig {
    @Bean
    public StringRedisTemplate template(RedisConnectionFactory factory){
        return new StringRedisTemplate(factory);
    }

    @Bean
    public RedissonClient redissonClient(){
        return Redisson.create();
    }
}
