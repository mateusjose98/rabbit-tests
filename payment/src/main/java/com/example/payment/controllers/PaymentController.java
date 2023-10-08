package com.example.payment.controllers;


import com.example.payment.model.Payment;
import com.example.payment.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    final PaymentService paymentService;

    @PostMapping
    public Long create(@RequestBody PaymentInput input) {
        return paymentService.save(new Payment(input));
    }
}
