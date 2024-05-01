package com.course.kafka.command.service;

import com.course.kafka.api.request.DiscountRequest;
import com.course.kafka.broker.message.DiscountMessage;
import com.course.kafka.command.action.DiscountAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleService {
    @Autowired
    private DiscountAction discountAction;


    public DiscountMessage send(DiscountRequest discountRequest) {
        return discountAction.send(discountRequest);
    }
}
