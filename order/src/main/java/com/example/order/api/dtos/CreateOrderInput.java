package com.example.order.api.dtos;

import java.util.List;

public record CreateOrderInput(String cpf, String email, List<ItemInput> items) {
}
