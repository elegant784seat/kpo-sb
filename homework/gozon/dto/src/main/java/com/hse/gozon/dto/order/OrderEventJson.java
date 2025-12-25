package com.hse.gozon.dto.order;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class OrderEventJson {
    private Long orderId;
    private String status;
    private Integer userId;
    private Double amount;
    private Instant createdAt;
}
