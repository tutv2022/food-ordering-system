package com.food.ordering.system.domain.valueobject;

import java.util.UUID;

public class ProductId extends BaseValueObjectId<UUID>
{
    public ProductId(UUID value) {
        super(value);
    }
}
