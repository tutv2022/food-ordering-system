package com.food.ordering.system.order.service.domain.mapper;

import com.food.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.ProductId;
import com.food.ordering.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderRequest;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Restaurant createOrderRequestToRestaurant(CreateOrderRequest createOrderRequest) {

        return Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderRequest.getRestaurantId()))
                .products(createOrderRequest.getItems().stream().map(
                        orderItem -> new Product(new ProductId((orderItem.getProductId())))
                ).collect(Collectors.toList()) )
                .build();
    }

    public Order createOrderRequestToOrder(CreateOrderRequest createOrderRequest) {
        return Order.builder()
                .customerId(new CustomerId(createOrderRequest.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderRequest.getRestaurantId()))
                .deliveryAddress(orderAddressToStreeAddress(createOrderRequest.getAddress()))
                .price(new Money(createOrderRequest.getPrice()))
                .items(orderItemsToOrderItemEntities(createOrderRequest.getItems()))
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemEntities(
            List<com.food.ordering.system.order.service.domain.dto.create.OrderItem> orderItems) {

        return orderItems.stream().map( orderItem -> OrderItem.builder()
                .product(new Product(new ProductId(orderItem.getProductId())))
                .price(new Money(orderItem.getPrice()))
                .quantity(orderItem.getQuantity())
                .subTotal(new Money(orderItem.getSubTotal()))
                .build()).collect(Collectors.toList());

    }

    private StreetAddress orderAddressToStreeAddress(OrderAddress orderAddress) {

        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostalCode(),
                orderAddress.getCity() );
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order) {

        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
