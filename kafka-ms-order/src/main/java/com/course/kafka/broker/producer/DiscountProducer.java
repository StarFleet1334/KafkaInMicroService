package com.course.kafka.broker.producer;


import com.course.kafka.api.request.DiscountRequest;
import com.course.kafka.broker.message.DiscountMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DiscountProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountProducer.class);

    @Autowired
    private KafkaTemplate<String, DiscountMessage> kafkaTemplate;


    public void publish(DiscountMessage discountMessage) {
        kafkaTemplate.send("t-commodity-promotion",discountMessage);

        LOGGER.info("Published Discount code: {} with percentage {}",discountMessage.getDiscountCode(),discountMessage.getDiscountPercentage());
    }




}
