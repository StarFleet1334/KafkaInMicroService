package com.course.kafka.broker.consumer;

import com.course.kafka.broker.message.DiscountMessage;
import com.course.kafka.broker.message.PromotionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "t-commodity-promotion")
public class PromotionConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PromotionConsumer.class);

    @KafkaHandler
    public void listenPromotion(PromotionMessage promotionMessage) {
        LOGGER.info("Processing promotion: {}",promotionMessage);
    }

    @KafkaHandler
    public void listenDiscount(DiscountMessage discountMessage) {
        LOGGER.info("Processing discount: {}",discountMessage);
    }
}
