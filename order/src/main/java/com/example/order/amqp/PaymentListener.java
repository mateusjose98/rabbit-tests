package com.example.order.amqp;


import com.example.order.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class PaymentListener {

    final OrderService orderService;

    @RabbitListener(queues = "payment.queue")
    public void recebeMensagem(PaymentInput msg) {
        log.info("{}", msg);
        System.out.println("UPDATE DE STATUS");
        if (msg.orderId() != null) {
            orderService.updateStatus(msg.orderId(), msg.status());
        }


    }
}
