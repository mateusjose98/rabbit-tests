package com.example.order.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Order {
    @Id
    private String id;
    private List<Item> items = new ArrayList<>();
    private String email;
    private String cpf;
    private String status;
}
