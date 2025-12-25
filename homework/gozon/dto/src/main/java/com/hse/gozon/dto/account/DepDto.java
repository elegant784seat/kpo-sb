package com.hse.gozon.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
@Schema(description = "Запрос на пополнение счета")
public class DepDto {
    @NotNull
    @Schema(description = "ID аккаунта", example = "1", required = true)
    private Integer accountId;
    @NotNull
    @DecimalMin(value = "0.01")
    @Schema(description = "Сумма пополнения",
            example = "500.00",
            minimum = "0.01",
            required = true)
    private BigDecimal amount;
}
