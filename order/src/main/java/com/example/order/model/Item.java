package com.example.order.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private BigDecimal unitaryAmount;
    private String name;

}
