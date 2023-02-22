package com.food.ordering.system.payment.service.domain.event;

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.payment.service.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

public class PaymentFailedEvent extends PaymentEvent{

    private DomainEventPublisher<PaymentFailedEvent> domainEventPublisher;

    public PaymentFailedEvent(Payment payment, ZonedDateTime createdAt, List<String> failureMessages, DomainEventPublisher<PaymentFailedEvent> domainEventPublisher) {
        super(payment, createdAt, failureMessages);
        this.domainEventPublisher = domainEventPublisher;
    }
    public PaymentFailedEvent(Payment payment, ZonedDateTime createdAt, DomainEventPublisher<PaymentFailedEvent> domainEventPublisher) {
        super(payment, createdAt, Collections.emptyList());
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public void fire() {
        domainEventPublisher.publish(this);
    }
}
