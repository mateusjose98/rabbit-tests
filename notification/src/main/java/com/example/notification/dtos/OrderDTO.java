package com.example.notification.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderDTO {
    private String id;
    private String email;
    private List<Item> items = new ArrayList<>();

    @ToString
    @Getter
    @Setter
    public static class Item {
        private BigDecimal unitaryAmount;
        private String name;

    }
}
