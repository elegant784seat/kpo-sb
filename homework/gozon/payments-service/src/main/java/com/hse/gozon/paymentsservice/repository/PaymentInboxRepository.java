package com.hse.gozon.paymentsservice.repository;

import com.hse.gozon.paymentsservice.model.PaymentInbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentInboxRepository extends JpaRepository<PaymentInbox, Long> {
    @Query(value = """
            SELECT * FROM payment_inbox
            WHERE processed_at IS NULL
            ORDER BY created_at ASC
            """, nativeQuery = true)
    List<PaymentInbox> findUnprocessed();
}
