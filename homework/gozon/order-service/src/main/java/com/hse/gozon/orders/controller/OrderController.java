package com.hse.gozon.orders.controller;

import com.hse.gozon.dto.order.OrderCreateRequestDto;
import com.hse.gozon.dto.order.OrderDto;
import com.hse.gozon.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderDto createOrder(@RequestBody OrderCreateRequestDto newOrder){
        return orderService.createOrder(newOrder);
    }

    @GetMapping("/{orderId}")
    public OrderDto findById(@PathVariable Long orderId){
        return orderService.findOrderById(orderId);
    }

    @GetMapping("/accounts/{accountId}")
    public List<OrderDto> findAllOrdersByAccountId(@PathVariable Integer accountId){
        return orderService.findAllOrdersByAccountId(accountId);
    }

}
