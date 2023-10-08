package com.example.payment.controllers;

import com.example.payment.model.PaymentStatus;
import com.example.payment.model.PaymentType;

import java.math.BigDecimal;

public record PaymentInput(String cpf, BigDecimal amount,
                           PaymentType type, PaymentStatus status, String email,
                           String orderId) {

}
