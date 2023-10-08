package com.example.notification.amqp;

import com.example.notification.dtos.OrderDTO;
import com.example.notification.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class NotificationOrderListener {

    final EmailService emailService;

    @RabbitListener(queues = "order.queue")
    public void listenerNotification(OrderDTO input) {
        System.out.println("PEDIDO RECEBIDO, VAMOS ENVIAR O EMAIL");
        emailService.send(input.getEmail(), "Pedido recebido " + input.getId(), "Email de teste com os detalhes do email");
        System.out.println(input);
    }
}
