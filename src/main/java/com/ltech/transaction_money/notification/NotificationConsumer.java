package com.ltech.transaction_money.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.ltech.transaction_money.authorization.AuthorizationRequest;
import com.ltech.transaction_money.authorization.AuthorizationResponse;
import com.ltech.transaction_money.transaction.Transaction;

@Service
public class NotificationConsumer {

    private RestClient restClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);

    public NotificationConsumer(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.notification.service/notify")
                .build();
    }

    @KafkaListener(topics = "transaction-notification", groupId = "picpay-desafio-backend")
    public void receiveNotification(Transaction transaction) {

        LOGGER.info("notifying transaction {}...", transaction);

        var response = restClient.post()
                .body(new AuthorizationRequest().generateInterBody())
                .retrieve()
                .toEntity(AuthorizationResponse.class);

        if (response.getStatusCode().isError() || !response.getBody().isAuthorized()) {
            throw new NotificationException("Error notifying transaction " + transaction);
        }

        LOGGER.info("notification has been sent {}...", response.getBody());
    }
}
