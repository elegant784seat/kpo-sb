package com.hse.gozon.gateway.client;

import com.hse.gozon.dto.order.OrderCreateRequestDto;
import com.hse.gozon.dto.order.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "order-service", url = "${order-service.url:http://localhost:9091}")
public interface OrderClient {

    @PostMapping("/api/orders")
    OrderDto createOrder(@RequestBody OrderCreateRequestDto newOrder);

    @GetMapping("/api/orders/{orderId}")
    OrderDto findById(@PathVariable Long orderId);

    @GetMapping("/api/orders/accounts/{accountId}")
    List<OrderDto> findAllOrdersByAccountId(@PathVariable Integer accountId);
}
