package com.hse.gozon.dto.account;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PaymentEventJson {
    private Integer accountId;
    private Double amount;
    private Long orderId;
    private String status;
    private Instant createdAt;
}

