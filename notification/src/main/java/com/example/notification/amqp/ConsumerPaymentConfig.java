package com.example.notification.amqp;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerPaymentConfig {

    @Value("${dlx.notification}")
    private String dlxName;
    // fila notificação-payment
    @Value("${ex.payment}")
    private String exPayment;
    @Value("${payment.notify}")
    private String paymentQueue;

    @Bean
    public Queue queuePayment() {
        return QueueBuilder
                .nonDurable(paymentQueue)
                .deadLetterExchange(dlxName)
                .build();
    }

    @Bean
    public FanoutExchange fanoutExchangePayment() {
        return ExchangeBuilder
                .fanoutExchange(exPayment)
                .build();
    }

    @Bean
    public Binding bindNotificationPayment() {
        return BindingBuilder
                .bind(queuePayment())
                .to(fanoutExchangePayment());
    }


}
