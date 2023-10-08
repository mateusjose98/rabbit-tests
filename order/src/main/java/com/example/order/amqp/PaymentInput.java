package com.example.order.amqp;

import java.math.BigDecimal;

public record PaymentInput(String cpf, BigDecimal amount, String type, String status, String email,
                           String orderId) {

}

