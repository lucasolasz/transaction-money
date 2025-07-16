package com.ltech.transaction_money.authorization;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.ltech.transaction_money.transaction.Transaction;
import com.ltech.transaction_money.transaction.UnauthorizedTransactionException;

@Service
public class AuthorizerService {

    private RestClient restClient;

    public AuthorizerService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.random.org/json-rpc/4/invoke")
                .build();
    }

    public void authorizeTransaction(Transaction transaction) {

        var response = restClient.post()
                .body(new AuthorizationRequest().generateInterBody())
                .retrieve()
                .toEntity(AuthorizationResponse.class);

        if (response.getStatusCode().isError() || !response.getBody().isAuthorized()) {
            throw new UnauthorizedTransactionException("Transaction not authorized");
        }

    }
}
