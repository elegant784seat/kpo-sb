package com.hse.gozon.gateway.controller;

import com.hse.gozon.dto.order.OrderCreateRequestDto;
import com.hse.gozon.dto.order.OrderDto;
import com.hse.gozon.gateway.client.OrderClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Управление заказами",
        description = "API для управления заказами пользователей")
public class OrderController {
    private final OrderClient orderClient;

    @PostMapping
    @Operation(summary = "Создать новый заказ",
            description = "Создает новый заказ для указанного пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Заказ успешно создан"),
            @ApiResponse(responseCode = "400", description = "Неверные входные данные"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public OrderDto createOrder(@Validated @RequestBody OrderCreateRequestDto newOrder) {
        return orderClient.createOrder(newOrder);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Получить информацию о заказе",
            description = "Возвращает информацию о заказе по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о заказе"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    public OrderDto findById(
            @Parameter(description = "ID заказа", example = "1001", required = true)
            @PathVariable Long orderId) {
        return orderClient.findById(orderId);
    }

    @GetMapping("/accounts/{accountId}")
    @Operation(summary = "Получить все заказы пользователя",
            description = "Возвращает список всех заказов для указанного аккаунта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список заказов пользователя"),
            @ApiResponse(responseCode = "404", description = "Аккаунт не найден")
    })
    public List<OrderDto> findOrdersByAccountId(
            @Parameter(description = "ID аккаунта", example = "1", required = true)
            @PathVariable Integer accountId) {
        return orderClient.findAllOrdersByAccountId(accountId);
    }
}
