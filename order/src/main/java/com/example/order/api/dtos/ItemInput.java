package com.example.order.api.dtos;

import java.math.BigDecimal;

public record ItemInput(BigDecimal unitaryAmount, String name) {
}
