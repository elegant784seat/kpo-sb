package com.hse.gozon.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "Запрос на создание аккаунта")
public class AccountCreateDto {
    @Size(max = 50)
    @Schema(description = "Имя пользователя",
            example = "Иван Иванов",
            maxLength = 50)
    private String name;
    @Email
    @Schema(description = "Email пользователя",
            example = "user@example.com",
            format = "email")
    private String email;
}
