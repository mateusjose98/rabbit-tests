package com.example.payment.model;


import com.example.payment.controllers.PaymentInput;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    @Enumerated(EnumType.STRING)
    private PaymentType type;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private String email;
    private String orderId;

    public Payment(PaymentInput input) {

        this.cpf = input.cpf();
        this.type = input.type();
        this.status = input.status();
        this.amount = input.amount();
        this.email = input.email();
        this.orderId = input.orderId();
    }
}
