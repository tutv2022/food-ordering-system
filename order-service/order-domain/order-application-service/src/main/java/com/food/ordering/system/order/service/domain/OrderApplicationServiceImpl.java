package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderRequest;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderRequest;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.ports.input.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
class OrderApplicationServiceImpl implements OrderApplicationService {

    OrderCreateRequestHandler orderCreateRequestHandler;

    OrderTrackRequestHandler orderTrackRequestHandler;

    public OrderApplicationServiceImpl(OrderCreateRequestHandler orderCreateRequestHandler, OrderTrackRequestHandler orderTrackRequestHandler) {
        this.orderCreateRequestHandler = orderCreateRequestHandler;
        this.orderTrackRequestHandler = orderTrackRequestHandler;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        return orderCreateRequestHandler.createOrder(createOrderRequest);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderRequest trackOrderRequest) {
        return orderTrackRequestHandler.trackOrder(trackOrderRequest);
    }
}
