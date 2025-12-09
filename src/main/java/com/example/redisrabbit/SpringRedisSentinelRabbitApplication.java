package com.example.redisrabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main Spring Boot Application
 * 
 * This application demonstrates integration of:
 * - Spring Boot
 * - Redis Sentinel (High Availability Redis)
 * - RabbitMQ (Message Broker)
 * 
 * @author Your Name
 * @version 1.0.0
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
public class SpringRedisSentinelRabbitApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisSentinelRabbitApplication.class, args);
    }
}

