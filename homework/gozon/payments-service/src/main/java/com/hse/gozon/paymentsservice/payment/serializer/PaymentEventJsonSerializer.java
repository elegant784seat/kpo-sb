package com.hse.gozon.paymentsservice.payment.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hse.gozon.dto.account.PaymentEventJson;
import com.hse.gozon.paymentsservice.exception.PaymentServiceException;
import com.hse.kafka.avro.event.PaymentEventAvro;
import com.hse.kafka.avro.event.PaymentStatusAvro;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentEventJsonSerializer {
    private final ObjectMapper objectMapper;


    public String serialize(PaymentEventAvro paymentEvent) {
        PaymentEventJson json = PaymentEventJson.builder()
                .accountId(paymentEvent.getAccountId())
                .amount(paymentEvent.getAmount())
                .createdAt(paymentEvent.getCreatedAt())
                .orderId(paymentEvent.getOrderId())
                .status(paymentEvent.getStatus().toString())
                .build();
        try {
            return objectMapper.writeValueAsString(json);
        } catch (JsonProcessingException exception) {
            throw new PaymentServiceException("ошибка при сериализации данных", exception);
        }
    }

    public PaymentEventAvro deserialize(String json) {
        try {
            PaymentEventJson jsonEvent = objectMapper.readValue(json, PaymentEventJson.class);
            return PaymentEventAvro.newBuilder()
                    .setStatus(PaymentStatusAvro.valueOf(jsonEvent.getStatus()))
                    .setOrderId(jsonEvent.getOrderId())
                    .setAccountId(jsonEvent.getAccountId())
                    .setAmount(jsonEvent.getAmount())
                    .setCreatedAt(jsonEvent.getCreatedAt())
                    .build();
        } catch (JsonProcessingException exception) {
            throw new PaymentServiceException("ошибка при сериализации данных", exception);
        }
    }
}
