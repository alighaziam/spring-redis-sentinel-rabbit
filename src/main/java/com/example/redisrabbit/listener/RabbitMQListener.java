package com.example.redisrabbit.listener;

import com.example.redisrabbit.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ Message Listener
 * 
 * Listens to messages from RabbitMQ queue and processes them.
 */
@Component
public class RabbitMQListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQListener.class);

    @RabbitListener(queues = "${spring.rabbitmq.queue.name:demo_queue}")
    public void receiveMessage(Message message) {
        logger.info("Received message: {}", message);
        
        try {
            // Process the message
            processMessage(message);
            logger.info("Message processed successfully: {}", message.getId());
        } catch (Exception e) {
            logger.error("Error processing message: {}", message.getId(), e);
            throw e; // Will trigger retry or DLQ
        }
    }

    private void processMessage(Message message) {
        // Simulate message processing
        logger.info("Processing message from {}: {}", message.getSender(), message.getContent());
        // Add your business logic here
    }
}

