package com.hse.gozon.paymentsservice.payment;

import com.hse.gozon.paymentsservice.exception.NotFoundException;
import com.hse.gozon.paymentsservice.model.Account;
import com.hse.gozon.paymentsservice.model.Payment;
import com.hse.gozon.paymentsservice.model.PaymentOutbox;
import com.hse.gozon.paymentsservice.model.PaymentStatus;
import com.hse.gozon.paymentsservice.payment.serializer.PaymentEventJsonSerializer;
import com.hse.gozon.paymentsservice.repository.AccountRepository;
import com.hse.gozon.paymentsservice.repository.PaymentOutboxRepository;
import com.hse.gozon.paymentsservice.repository.PaymentRepository;
import com.hse.kafka.avro.event.PaymentEventAvro;
import com.hse.kafka.avro.event.PaymentStatusAvro;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.hse.gozon.paymentsservice.mapper.PaymentMapper.toEntity;
import static com.hse.gozon.paymentsservice.mapper.PaymentOutboxMapper.toOutbox;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentProcessorService {
    private final PaymentRepository paymentRepository;
    private final PaymentOutboxRepository outboxRepository;
    private final AccountRepository accountRepository;
    private final PaymentEventJsonSerializer jsonSerializer;

    @Transactional
    public void processPaymentEvent(PaymentEventAvro paymentEvent, String eventId) {
        Account account = getAccountById(paymentEvent.getAccountId());
        BigDecimal orderCost = BigDecimal.valueOf(paymentEvent.getAmount());
        int updatedRows = accountRepository.withDraw(account.getId(), orderCost);
        Payment payment = toEntity(paymentEvent);
        if (updatedRows == 0) {
            payment.setStatus(PaymentStatus.CANCELLED);
            paymentEvent.setStatus(PaymentStatusAvro.CANCELLED);
            log.error("недостаточно денег на аккаунте");
        } else {
            payment.setStatus(PaymentStatus.APPROVED);
            paymentEvent.setStatus(PaymentStatusAvro.APPROVED);
        }
        paymentRepository.save(payment);
        log.debug("списание средств прошло успешно");
        PaymentOutbox outbox = toOutbox(payment, jsonSerializer.serialize(paymentEvent), eventId);
        outboxRepository.save(outbox);
    }

    private Account getAccountById(Integer id) {
        return accountRepository.findById(id).orElseThrow(() ->
                new NotFoundException("аккаунт с id" + id + " не найден"));
    }
}
