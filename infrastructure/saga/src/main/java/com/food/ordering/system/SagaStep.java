package com.food.ordering.system;

import com.food.ordering.system.domain.event.DomainEvent;

public interface SagaStep<T> {
    void process(T data);
    void rollback(T data);
}
