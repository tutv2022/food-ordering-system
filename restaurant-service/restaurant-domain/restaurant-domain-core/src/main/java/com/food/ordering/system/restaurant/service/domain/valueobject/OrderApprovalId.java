package com.food.ordering.system.restaurant.service.domain.valueobject;

import com.food.ordering.system.domain.entity.BaseEntity;
import com.food.ordering.system.domain.valueobject.BaseValueObjectId;

import java.util.UUID;

public class OrderApprovalId extends BaseValueObjectId<UUID> {

    public OrderApprovalId(UUID value) {
        super(value);
    }
}
