package com.example.notification.amqp;

import com.example.notification.dtos.PaymentInput;
import com.example.notification.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import static org.springframework.util.StringUtils.hasText;

@Configuration
@RequiredArgsConstructor
public class NotificationPaymentListener {

    final EmailService emailService;

    @RabbitListener(queues = "payment.notify")
    public void listenerNotification(PaymentInput input) {
        System.out.println("PAGAMENTO RECEBIDO! SUCESSO OU FALHA?");
        // simulação de erro para mover para DLQ
        if (!hasText(input.email()) || input.email().equals("josemateus2.ufma@gmail.com")) { //validação de algo importante ou erro na própria mensagem
            System.out.println("Erro: " + input);
            throw new RuntimeException("Erro, email inválido");
        }
        emailService.send(input.email(), "PAGAMENTO FOI PROCESSADO COM STATUS " + input.status(), "Mensagem qualquer");

    }

}
