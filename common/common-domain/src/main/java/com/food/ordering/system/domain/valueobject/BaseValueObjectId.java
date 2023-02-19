package com.food.ordering.system.domain.valueobject;

import java.util.Objects;

public abstract class BaseValueObjectId <T> {

    private final T value;

    protected BaseValueObjectId(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseValueObjectId<?> that = (BaseValueObjectId<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
