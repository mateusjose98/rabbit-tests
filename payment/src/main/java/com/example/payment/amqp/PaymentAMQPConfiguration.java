package com.example.payment.amqp;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PaymentAMQPConfiguration {

    /*
     * Plugando a exchange chamada 'paymenyt.ex' a nossa aplicação
     * */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("payment.ex");
    }

    @Bean
    public RabbitAdmin criaRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    /*
     * Config do RabbitTemplate que é a interface de acesso ao rabbit,
     * com ela fazemos o envio das mensagens, estamos usando o converter definido
     * */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    /*
     * Bean para serializar os objetos enviados nas mensagens, dessa forma não precisamos lidar diretamente com string/byte[]
     * */
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /*
     * Tenta inicializar o rabbitAdmin no startup da aplicação, precisa que o rabbit esteja de pé
     * */
    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin rabbitAdmin) {

        return event -> {
            try {
                rabbitAdmin.initialize();
            } catch (Exception e) {
                System.out.println("Conexão não estabelecida com RabbitMQ " + e.getMessage());
            }

        };
    }


}
