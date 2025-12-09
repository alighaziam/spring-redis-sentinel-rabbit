package com.example.redisrabbit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Message model for RabbitMQ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    private String id;
    private String content;
    private String sender;
    private LocalDateTime timestamp;
}

