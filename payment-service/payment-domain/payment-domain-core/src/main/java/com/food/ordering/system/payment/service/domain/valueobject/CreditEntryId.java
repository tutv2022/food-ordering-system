package com.food.ordering.system.payment.service.domain.valueobject;

import com.food.ordering.system.domain.valueobject.BaseValueObjectId;

import java.util.UUID;

public class CreditEntryId extends BaseValueObjectId<UUID> {
    public CreditEntryId(UUID value) {
        super(value);
    }
}
