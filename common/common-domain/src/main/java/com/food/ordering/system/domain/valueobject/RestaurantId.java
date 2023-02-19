package com.food.ordering.system.domain.valueobject;

import java.util.UUID;

public class RestaurantId extends BaseValueObjectId<UUID>
{
    public RestaurantId(UUID value) {
        super(value);
    }
}
