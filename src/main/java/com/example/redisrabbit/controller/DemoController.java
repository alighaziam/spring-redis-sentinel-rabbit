package com.example.redisrabbit.controller;

import com.example.redisrabbit.model.Message;
import com.example.redisrabbit.service.RabbitMQService;
import com.example.redisrabbit.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Demo REST Controller
 * 
 * Provides REST endpoints to demonstrate Redis and RabbitMQ integration.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RabbitMQService rabbitMQService;

    /**
     * Store data in Redis
     */
    @PostMapping("/redis/set")
    public ResponseEntity<Map<String, Object>> setRedisValue(
            @RequestParam String key,
            @RequestParam String value,
            @RequestParam(required = false, defaultValue = "3600") long ttlSeconds) {
        
        redisService.set(key, value, ttlSeconds, TimeUnit.SECONDS);
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("key", key);
        response.put("message", "Value stored in Redis");
        return ResponseEntity.ok(response);
    }

    /**
     * Get data from Redis
     */
    @GetMapping("/redis/get/{key}")
    public ResponseEntity<Map<String, Object>> getRedisValue(@PathVariable String key) {
        Object value = redisService.get(key);
        
        Map<String, Object> response = new HashMap<>();
        if (value != null) {
            response.put("status", "success");
            response.put("key", key);
            response.put("value", value);
        } else {
            response.put("status", "not_found");
            response.put("key", key);
            response.put("message", "Key not found in Redis");
        }
        return ResponseEntity.ok(response);
    }

    /**
     * Delete data from Redis
     */
    @DeleteMapping("/redis/delete/{key}")
    public ResponseEntity<Map<String, Object>> deleteRedisValue(@PathVariable String key) {
        redisService.delete(key);
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("key", key);
        response.put("message", "Key deleted from Redis");
        return ResponseEntity.ok(response);
    }

    /**
     * Check if key exists in Redis
     */
    @GetMapping("/redis/exists/{key}")
    public ResponseEntity<Map<String, Object>> checkRedisKey(@PathVariable String key) {
        Boolean exists = redisService.exists(key);
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("key", key);
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    /**
     * Send message to RabbitMQ
     */
    @PostMapping("/rabbitmq/send")
    public ResponseEntity<Map<String, Object>> sendMessage(@RequestBody Map<String, String> request) {
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setContent(request.getOrDefault("content", "Hello from RabbitMQ!"));
        message.setSender(request.getOrDefault("sender", "API"));
        message.setTimestamp(LocalDateTime.now());
        
        rabbitMQService.sendMessage(message);
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("messageId", message.getId());
        response.put("message", "Message sent to RabbitMQ");
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("redis", redisService.exists("health_check") ? "CONNECTED" : "CHECKING");
        health.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(health);
    }
}

