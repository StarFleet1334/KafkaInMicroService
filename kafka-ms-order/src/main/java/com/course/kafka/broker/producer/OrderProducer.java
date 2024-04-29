package com.course.kafka.broker.producer;

import com.course.kafka.broker.message.OrderMessage;
import org.hibernate.sql.exec.spi.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;

@Service
public class OrderProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    @Autowired
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;

    public void publish(OrderMessage message) {
        ListenableFuture<SendResult<String, OrderMessage>> listenableFuture = (ListenableFuture<SendResult<String, OrderMessage>>) kafkaTemplate.send("t-commodity-order", message.getOrderNumber(), message);

        CompletableFuture<SendResult<String, OrderMessage>> completableFuture = toCompletableFuture(listenableFuture);

        completableFuture.thenAccept(result -> {
            LOGGER.info("Order {}, item {} published successfully", message.getOrderNumber(), message.getItemName());
        }).exceptionally(ex -> {
            LOGGER.warn("Order {}, item {} failed to publish because {}", message.getOrderNumber(), message.getItemName(), ex.getMessage());
            return null;
        });
    }

    private <T> CompletableFuture<T> toCompletableFuture(ListenableFuture<T> listenableFuture) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        listenableFuture.addCallback(completableFuture::complete, completableFuture::completeExceptionally);
        return completableFuture;
    }
}
