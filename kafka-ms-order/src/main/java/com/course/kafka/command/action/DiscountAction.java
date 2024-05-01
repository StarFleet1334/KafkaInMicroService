package com.course.kafka.command.action;

import com.course.kafka.api.request.DiscountRequest;
import com.course.kafka.broker.message.DiscountMessage;
import com.course.kafka.broker.producer.DiscountProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscountAction {

    @Autowired
    private DiscountProducer discountProducer;

    public DiscountMessage send(DiscountRequest discountRequest) {
        var discountMessage = new DiscountMessage(discountRequest.getDiscountCode(),discountRequest.getDiscountPercentage());
        discountProducer.publish(discountMessage);
        return discountMessage;
    }
}
