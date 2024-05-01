package com.course.kafka.broker.producer;

import com.course.kafka.broker.message.OrderMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.hibernate.sql.exec.spi.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    @Autowired
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;

    public void publish(OrderMessage message) {
//        ListenableFuture<SendResult<String, OrderMessage>> listenableFuture = (ListenableFuture<SendResult<String, OrderMessage>>) kafkaTemplate.send("t-commodity-order", message.getOrderNumber(), message);
//
//        CompletableFuture<SendResult<String, OrderMessage>> completableFuture = toCompletableFuture(listenableFuture);
//
//        completableFuture.thenAccept(result -> {
//            LOGGER.info("Order {}, item {} published successfully", message.getOrderNumber(), message.getItemName());
//        }).exceptionally(ex -> {
//            LOGGER.warn("Order {}, item {} failed to publish because {}", message.getOrderNumber(), message.getItemName(), ex.getMessage());
//            return null;
//        });
        var producerRecord = buildProducerRecord(message);
        kafkaTemplate.send(producerRecord);
        LOGGER.info("Order {}, item {} published successfully", message.getOrderNumber(), message.getItemName());
        
    }

    private ProducerRecord<String, OrderMessage> buildProducerRecord(OrderMessage message) {
        var surpriseBonus = StringUtils.startsWithIgnoreCase(message.getOrderLocation(), "A") ? 25 : 15;
        var headers = new ArrayList<Header>();
        var surpriseBonusHeader = new RecordHeader("surpriseBonus", Integer.toString(surpriseBonus).getBytes());

        headers.add(surpriseBonusHeader);

        return new ProducerRecord<String, OrderMessage>("t-commodity-order", null, message.getOrderNumber(), message,
                headers);
    }

//    private <T> CompletableFuture<T> toCompletableFuture(ListenableFuture<T> listenableFuture) {
//        CompletableFuture<T> completableFuture = new CompletableFuture<>();
//        listenableFuture.addCallback(completableFuture::complete, completableFuture::completeExceptionally);
//        return completableFuture;
//    }
}
