package com.food.ordering.system.order.service.domain.outbox.scheduler.approval;

import com.food.ordering.system.saga.SagaStatus;
import com.food.ordering.system.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import com.food.ordering.system.outbox.OutboxScheduler;
import com.food.ordering.system.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RestaurantApprovalOutboxCleanScheduler implements OutboxScheduler {

    private final ApprovalOutboxHelper approvalOutboxHelper;

    public RestaurantApprovalOutboxCleanScheduler(ApprovalOutboxHelper approvalOutboxHelper) {
        this.approvalOutboxHelper = approvalOutboxHelper;
    }

    @Override
    @Scheduled(cron ="@midnight")
    public void processOutboxMessage() {
        Optional<List<OrderApprovalOutboxMessage>> orderApprovalOutboxMessages =
            approvalOutboxHelper.getApprovalOutboxMessageByOutboxStatusAndSagaStatus(
                OutboxStatus.COMPLETED,
                SagaStatus.COMPENSATED,
                SagaStatus.FAILED,
                SagaStatus.COMPENSATED
        );
        if (orderApprovalOutboxMessages.isPresent()) {
            List<OrderApprovalOutboxMessage> outboxMessages = orderApprovalOutboxMessages.get();
            log.info("Received {} OrderApprovalOutboxMessage for clean up. The payloads: {}",
                     outboxMessages.size(),
                     outboxMessages.stream().map(OrderApprovalOutboxMessage::getPayLoad).collect(Collectors.joining("\n")));
            approvalOutboxHelper.deleteApprovalOutboxMessageByOutboxStatusAndSagaStatus(
                    OutboxStatus.COMPLETED,
                    SagaStatus.COMPENSATED,
                    SagaStatus.FAILED,
                    SagaStatus.COMPENSATED
            );
            log.info("{} OrderApprovalOutboxMessage deleted!", outboxMessages.size());
        }
    }
}
