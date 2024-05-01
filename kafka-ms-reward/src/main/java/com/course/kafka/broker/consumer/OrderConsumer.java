package com.course.kafka.broker.consumer;


import com.course.kafka.broker.message.OrderMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);


    @KafkaListener(topics = "t-commodity-order")
    public void listen(ConsumerRecord<String, OrderMessage> consumerRecord) {
        var headers = consumerRecord.headers();
        var orderMessage = consumerRecord.value();

        LOGGER.info("Processing order {}, item {}, credit card number {}",
                orderMessage.getOrderNumber(),
                orderMessage.getItemName(),
                orderMessage.getCreditCardNumber());

        LOGGER.info("Headers: {}",headers);
        headers.forEach(h -> LOGGER.info("  key : {}, value: {} ",h.key(),h.value()));

        var headerValue = ObjectUtils.isEmpty(headers.lastHeader("surpriseBonus").value()) ? "0" : new String(headers.lastHeader("surpriseBonus").value());


        var bonusPercentage = Integer.parseInt(headerValue);
        var bonusAmount = (bonusPercentage / 100) * orderMessage.getPrice() * orderMessage.getQuantity();

        LOGGER.info("Bonus amount is {}",bonusAmount);
    }

}
