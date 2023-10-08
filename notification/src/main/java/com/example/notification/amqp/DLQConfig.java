package com.example.notification.amqp;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DLQConfig {

    @Value("${dlq.notification}")
    private String dlqName;

    @Value("${dlx.notification}")
    private String dlxName;

    @Bean
    public Queue queueDQL() {
        return QueueBuilder
                .nonDurable(dlqName)
                .build();
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        return ExchangeBuilder
                .fanoutExchange(dlxName)
                .build();
    }

    @Bean
    public Binding bindDQL() {
        return BindingBuilder
                .bind(queueDQL())
                .to(deadLetterExchange());
    }


}
