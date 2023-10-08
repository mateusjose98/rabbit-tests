package com.example.order.api;

import com.example.order.api.dtos.CreateOrderInput;
import com.example.order.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("order")
public class OrderController {

    final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateOrderInput input) {
        String orderId = orderService.save(input);
        return ResponseEntity.ok(orderId);
    }

}
