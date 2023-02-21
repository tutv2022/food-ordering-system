package com.food.ordering.system.payment.service.domain.valueobject;

import com.food.ordering.system.domain.valueobject.BaseValueObjectId;

import java.util.UUID;

public class CreditHistoryId extends BaseValueObjectId<UUID> {
    public CreditHistoryId(UUID value) {
        super(value);
    }
}
