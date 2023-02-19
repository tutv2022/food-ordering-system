package com.food.ordering.system.order.service.domain.valueobject;

import com.food.ordering.system.domain.valueobject.BaseValueObjectId;

import java.util.UUID;

public class TrackingId extends BaseValueObjectId<UUID> {

    public TrackingId(UUID value) {
        super(value);
    }
}
