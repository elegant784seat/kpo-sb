package com.hse.gozon.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
@Schema(description = "Запрос на создание заказа")
public class OrderCreateRequestDto {
    @NotNull
    @DecimalMin(value = "0.01")
    @Schema(description = "Общая сумма заказа",
            example = "250.50",
            minimum = "0.01",
            required = true)
    private BigDecimal totalAmount;

    @NotNull
    @Schema(description = "ID пользователя", example = "1", required = true)
    private Integer userId;
}
