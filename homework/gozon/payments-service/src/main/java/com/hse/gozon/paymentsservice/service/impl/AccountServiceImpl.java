package com.hse.gozon.paymentsservice.service.impl;

import com.hse.gozon.dto.account.AccountCreateDto;
import com.hse.gozon.dto.account.AccountDto;
import com.hse.gozon.dto.account.DepDto;
import com.hse.gozon.paymentsservice.exception.EmailAlreadyExistsException;
import com.hse.gozon.paymentsservice.exception.NotFoundException;
import com.hse.gozon.paymentsservice.model.Account;
import com.hse.gozon.paymentsservice.repository.AccountRepository;
import com.hse.gozon.paymentsservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hse.gozon.paymentsservice.mapper.AccountMapper.toDto;
import static com.hse.gozon.paymentsservice.mapper.AccountMapper.toEntity;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public AccountDto createAccount(AccountCreateDto newAccount) {
        if (accountRepository.existsByEmail(newAccount.getEmail())) {
            throw new EmailAlreadyExistsException("пользователь с email" + newAccount.getEmail() + "уже существует");
        }
        Account account = accountRepository.save(toEntity(newAccount));
        log.debug("пользователь{} сохранен", account.getEmail());
        return toDto(account);
    }

    @Override
    public AccountDto findAccountById(Integer id) {
        Account account = getAccountById(id);
        log.debug("пользователь с id{} успешно найден", id);
        return toDto(account);

    }

    @Override
    @Transactional
    public AccountDto deposit(DepDto depDto) {
        if (!accountRepository.existsById(depDto.getAccountId())) {
            throw new NotFoundException("пользователь с id " + depDto.getAccountId() + " не найден");
        }
        accountRepository.deposit(depDto.getAccountId(), depDto.getAmount());
        Account account = getAccountById(depDto.getAccountId());
        log.debug("счет успешно пополнен{}", account.getBalance());
        return toDto(account);
    }

    private Account getAccountById(Integer id) {
        return accountRepository.findById(id).orElseThrow(() ->
                new NotFoundException("пользователь с id " + " не найден"));
    }
}
