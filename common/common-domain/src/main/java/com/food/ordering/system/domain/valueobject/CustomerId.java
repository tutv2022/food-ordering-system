package com.food.ordering.system.domain.valueobject;

import java.util.UUID;

public class CustomerId extends BaseValueObjectId<UUID>
{
    public CustomerId(UUID value) {
        super(value);
    }
}
