package com.food.ordering.system.order.service.domain.outbox.scheduler.approval;

import com.food.ordering.system.saga.SagaStatus;
import com.food.ordering.system.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval.RestaurantApprovalRequestMessagePublisher;
import com.food.ordering.system.outbox.OutboxScheduler;
import com.food.ordering.system.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RestaurantApprovalOutboxScheduler implements OutboxScheduler {

    private final ApprovalOutboxHelper approvalOutboxHelper;
    private final RestaurantApprovalRequestMessagePublisher restaurantApprovalRequestMessagePublisher;

    public RestaurantApprovalOutboxScheduler(ApprovalOutboxHelper approvalOutboxHelper,
                                             RestaurantApprovalRequestMessagePublisher restaurantApprovalRequestMessagePublisher) {
        this.approvalOutboxHelper = approvalOutboxHelper;
        this.restaurantApprovalRequestMessagePublisher = restaurantApprovalRequestMessagePublisher;
    }

    @Override
    @Transactional
    @Scheduled(fixedDelayString = "${order-service.outbox-scheduler-fixed-rate}",
            initialDelayString = "${order-service.outbox-scheduler-initial-delay}")
    public void processOutboxMessage() {

        Optional<List<OrderApprovalOutboxMessage>> outboxMessageList =
                approvalOutboxHelper.getApprovalOutboxMessageByOutboxStatusAndSagaStatus(
                OutboxStatus.STARTED,
                SagaStatus.PROCESSING
        );

        if (outboxMessageList.isPresent() && outboxMessageList.get().size() > 0) {

            List<OrderApprovalOutboxMessage> orderApprovalOutboxMessages = outboxMessageList.get();

            log.info("Received {} OrderApprovalOutboxMessage with id: {}, sending to message bus!",
                     orderApprovalOutboxMessages.size(),
                     orderApprovalOutboxMessages.stream().map( orderApprovalOutboxMessage ->
                             orderApprovalOutboxMessage.getId().toString()).collect(Collectors.joining(",")));

            orderApprovalOutboxMessages.forEach(orderApprovalOutboxMessage ->
                    restaurantApprovalRequestMessagePublisher.publish(orderApprovalOutboxMessage, this::updateOutboxStatus));

            log.info("{} OrderApprovalOutboxMessage sent to message bus!", orderApprovalOutboxMessages.size());
        }
    }

    private void updateOutboxStatus(OrderApprovalOutboxMessage orderApprovalOutboxMessage, OutboxStatus outboxStatus) {

        orderApprovalOutboxMessage.setOutboxStatus(outboxStatus);

        approvalOutboxHelper.save(orderApprovalOutboxMessage);

        log.info("OrderApprovalOutboxMessage is updated with outbox status: {}", outboxStatus.name());

    }
}
