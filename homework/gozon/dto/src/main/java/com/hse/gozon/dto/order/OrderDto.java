package com.hse.gozon.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Информация о заказе")
public class OrderDto {
    @Schema(description = "Общая сумма заказа", example = "250.50")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalAmount;
    @Schema(description = "ID пользователя", example = "1")
    private Integer userId;
    @Schema(description = "Статус заказа",
            example = "FINISHED",
            allowableValues = {"NEW", "FINISHED", "CANCELLED"})
    private String status;
}
