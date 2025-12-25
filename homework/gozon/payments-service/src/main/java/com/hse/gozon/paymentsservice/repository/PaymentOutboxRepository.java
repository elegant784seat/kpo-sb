package com.hse.gozon.paymentsservice.repository;

import com.hse.gozon.paymentsservice.model.PaymentInbox;
import com.hse.gozon.paymentsservice.model.PaymentOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentOutboxRepository extends JpaRepository<PaymentOutbox, Long> {

    @Query(value =
            """
            SELECT * FROM payment_outbox
            WHERE processed_at IS NULL
            ORDER BY created_at ASC
            """, nativeQuery = true)
    List<PaymentOutbox> findUnprocessed();
}
