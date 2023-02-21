package com.food.ordering.system.payment.service.domain.exception;

import com.food.ordering.system.domain.exception.DomainException;

public class PaymentNotfoundException extends DomainException {


    public PaymentNotfoundException(String message) {
        super(message);
    }

    public PaymentNotfoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
