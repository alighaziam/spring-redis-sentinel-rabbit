package com.example.redisrabbit.service;

import com.example.redisrabbit.config.RabbitMQConfig;
import com.example.redisrabbit.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RabbitMQ Service
 * 
 * Provides message publishing functionality using RabbitMQ.
 */
@Service
public class RabbitMQService {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Send a message to the queue
     */
    public void sendMessage(Message message) {
        logger.info("Sending message: {}", message);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                message
        );
        logger.info("Message sent successfully: {}", message.getId());
    }

    /**
     * Send a simple string message
     */
    public void sendMessage(String message) {
        Message msg = new Message();
        msg.setId(String.valueOf(System.currentTimeMillis()));
        msg.setContent(message);
        msg.setSender("System");
        msg.setTimestamp(java.time.LocalDateTime.now());
        sendMessage(msg);
    }
}

