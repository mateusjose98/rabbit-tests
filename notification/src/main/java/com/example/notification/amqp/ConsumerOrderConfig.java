package com.example.notification.amqp;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerOrderConfig {

    @Value("${order.queue}")
    private String orderQueue;
    @Value("${order.ex}")
    private String exOrder;

    @Bean
    public Queue queueOrderNotification() {
        return QueueBuilder
                .nonDurable(orderQueue)
                .build();
    }


    @Bean
    public FanoutExchange fanoutExchangeOrder() {
        return ExchangeBuilder
                .fanoutExchange(exOrder)
                .build();
    }

    @Bean
    public Binding bindOrderNotification() {
        return BindingBuilder
                .bind(queueOrderNotification())
                .to(fanoutExchangeOrder());
    }
}
