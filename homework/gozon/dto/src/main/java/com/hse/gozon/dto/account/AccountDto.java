package com.hse.gozon.dto.account;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Информация об аккаунте")
public class AccountDto {
    @Schema(description = "Имя пользователя", example = "Иван Иванов")
    private String name;

    @Schema(description = "Email пользователя", example = "user@example.com")
    private String email;

    @Schema(description = "Баланс счета", example = "1000.00")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal balance;
}
