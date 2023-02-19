package com.food.ordering.system.order.service.domain.valueobject;

import com.food.ordering.system.domain.valueobject.BaseValueObjectId;

public class OrderItemId extends BaseValueObjectId<Long> {

    public OrderItemId(Long value) {
        super(value);
    }
}
