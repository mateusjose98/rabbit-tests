package com.example.order.amqp;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {

    /*
     * Plugando a exchange chamada 'order.ex' a nossa aplicação
     * */

    @Value("${order.ex}")
    private String orderExchange;

    @Bean
    public FanoutExchange fanoutExchangeOrderExchange() {
        return new FanoutExchange(orderExchange);
    }


}
