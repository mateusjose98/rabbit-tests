package com.example.payment.services;

import com.example.payment.model.Payment;
import com.example.payment.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaymentService {

    final RabbitTemplate rabbitTemplate;
    final PaymentRepository paymentRepository;

    @Transactional
    public Long save(Payment input) {
        input = paymentRepository.save(input);
        rabbitTemplate.convertAndSend("payment.ex", "", input);
        return input.getId();
    }

}
