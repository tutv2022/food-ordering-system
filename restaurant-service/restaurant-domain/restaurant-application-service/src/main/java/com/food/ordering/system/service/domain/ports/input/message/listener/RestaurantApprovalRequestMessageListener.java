package com.food.ordering.system.service.domain.ports.input.message.listener;

import com.food.ordering.system.service.domain.dto.RestaurantApprovalRequest;

public interface RestaurantApprovalRequestMessageListener {

    void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest);
}
