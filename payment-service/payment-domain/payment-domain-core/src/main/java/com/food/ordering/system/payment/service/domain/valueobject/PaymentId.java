package com.food.ordering.system.payment.service.domain.valueobject;

import com.food.ordering.system.domain.valueobject.BaseValueObjectId;

import java.util.UUID;

public class PaymentId extends BaseValueObjectId<UUID> {
    public PaymentId(UUID value) {
        super(value);
    }
}
