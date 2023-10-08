package com.example.order.amqp;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerConfig {

    @Bean
    public Queue queueOrderDetails() {
        return QueueBuilder
                .nonDurable("payment.queue")
                .build();
    }

    /*
     *
     * Sinalizando que vamos usar essa exchange payment.ex
     * */
    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder
                .fanoutExchange("payment.ex")
                .build();
    }

    /*
     * */
    @Bean
    public Binding bindPagamentoPedido(FanoutExchange fanoutExchange) {
        return BindingBuilder
                .bind(queueOrderDetails())
                .to(fanoutExchange());
    }
}
