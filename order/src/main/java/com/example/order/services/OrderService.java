package com.example.order.services;

import com.example.order.api.dtos.CreateOrderInput;
import com.example.order.model.Item;
import com.example.order.model.Order;
import com.example.order.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderService {

    final OrderRepository orderRepository;
    final RabbitTemplate rabbitTemplate;
    @Value(
            "${order.ex}"
    )
    private String fanoutExchange;

    public String save(CreateOrderInput input) {
        log.info("Order recebido: {}", input);
        Order toSave = Order.builder()
                .email(input.email())
                .status("CREATED")
                .cpf(input.cpf())
                .items(
                        input.items().stream().map(item -> Item.builder().name(item.name()).unitaryAmount(item.unitaryAmount()).build()).toList()
                ).build();
        Order saved = orderRepository.save(toSave);
        rabbitTemplate.convertAndSend(fanoutExchange, "", saved);
        return saved.getId();
    }

    // TODO refatorar
    public void updateStatus(String id, String status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Não encontramos o pedido com este id"));
        order.setStatus(status);
        orderRepository.save(order);
    }
}
