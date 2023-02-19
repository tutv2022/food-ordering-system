package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.domain.valueobject.*;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderRequest;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.food.ordering.system.order.service.domain.dto.create.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.input.OrderApplicationService;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {

    @Autowired
    private OrderApplicationService orderApplicationService;

    @Autowired
    private OrderDataMapper orderDataMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private CreateOrderRequest createOrderRequest;

    private CreateOrderRequest createOrderRequestWrongPrice;

    private CreateOrderRequest createOrderRequestWrongProductPrice;

    private final UUID CUSTOMER_ID = UUID.fromString("509ac198-b061-11ed-afa1-0242ac120002");
    private final UUID RESTAURANT_ID = UUID.fromString("5d4956ca-b061-11ed-afa1-0242ac120002");
    private final UUID PRODUCT_ID = UUID.fromString("6dee00c0-b061-11ed-afa1-0242ac120002");
    private final UUID PRODUCT_ID2 = UUID.fromString("6dee00c2-b061-11ed-afa1-0242ac120002");
    private final UUID ORDER_ID = UUID.fromString("7abd6ef8-b061-11ed-afa1-0242ac120002");
    private final BigDecimal PRICE = new BigDecimal("200.00" );

    @BeforeAll
    public void init() {
        createOrderRequest = CreateOrderRequest.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("LinhDam")
                        .postalCode("9000")
                        .city("Hanoi")
                        .build())
                .price(PRICE)
                .items(List.of(
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID2)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()

                ))
                .build();

        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));

        Restaurant restaurantResult = Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderRequest.getRestaurantId()))
                .products(
                    List.of(
                        new Product(new ProductId(PRODUCT_ID), "Product-1", new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID2), "Product-2", new Money(new BigDecimal("50.00")))
                    )
                )
                .active(true)
                .build();

        Order order = orderDataMapper.createOrderRequestToOrder(createOrderRequest);
        order.setId(new OrderId(ORDER_ID));

        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderRequestToRestaurant(createOrderRequest)))
                .thenReturn(Optional.of(restaurantResult));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }

    @Test
    public void testCreateOrder(){
        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderRequest);
        assertEquals(createOrderResponse.getOrderStatus(), OrderStatus.PENDING);
        assertEquals(createOrderResponse.getMessage(), "Order created successfully");
        assertNotNull(createOrderResponse.getOrderTrackingId());
    }


}
