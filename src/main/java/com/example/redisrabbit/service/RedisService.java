package com.example.redisrabbit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Redis Service
 * 
 * Provides caching and key-value operations using Redis Sentinel.
 */
@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Set a key-value pair with expiration
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * Set a key-value pair without expiration
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Get value by key
     */
    @Cacheable(value = "cache", key = "#key")
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Delete a key
     */
    @CacheEvict(value = "cache", key = "#key")
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Check if key exists
     */
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * Set expiration for a key
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * Get TTL (Time To Live) for a key
     */
    public Long getTtl(String key) {
        return redisTemplate.getExpire(key);
    }
}

