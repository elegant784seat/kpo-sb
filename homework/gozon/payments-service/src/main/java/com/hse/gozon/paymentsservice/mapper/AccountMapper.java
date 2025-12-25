package com.hse.gozon.paymentsservice.mapper;

import com.hse.gozon.dto.account.AccountCreateDto;
import com.hse.gozon.dto.account.AccountDto;
import com.hse.gozon.paymentsservice.model.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountMapper {
    public static AccountDto toDto(Account account) {
        return AccountDto.builder()
                .balance(account.getBalance())
                .email(account.getEmail())
                .name(account.getName())
                .build();
    }

    public static Account toEntity(AccountCreateDto newAccount) {
        return Account.builder()
                .name(newAccount.getName())
                .email(newAccount.getEmail())
                .balance(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
