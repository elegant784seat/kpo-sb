package com.hse.gozon.paymentsservice.payment;

import com.hse.gozon.paymentsservice.model.PaymentInbox;
import com.hse.gozon.paymentsservice.payment.serializer.PaymentEventJsonSerializer;
import com.hse.gozon.paymentsservice.repository.PaymentInboxRepository;
import com.hse.kafka.avro.event.PaymentEventAvro;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentsService {
    private final PaymentProcessorService processorService;
    private final PaymentInboxRepository inboxRepository;
    private final PaymentEventJsonSerializer jsonSerializer;

    @Scheduled(fixedDelay = 2000)
    public void handlePaymentEvent() {
        List<PaymentInbox> inboxes = inboxRepository.findUnprocessed();
        for (PaymentInbox inbox : inboxes) {
            try {
                PaymentEventAvro paymentEvent = jsonSerializer.deserialize(inbox.getPayload());
                processorService.processPaymentEvent(paymentEvent, inbox.getEventId());
                inbox.setProcessedAt(LocalDateTime.now());
                inboxRepository.save(inbox);
            } catch (Exception exception) {
                log.error("произошла ошибка во время обработки платежа", exception);
            }
        }
    }
}
