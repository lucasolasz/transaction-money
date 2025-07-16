package com.ltech.transaction_money.authorization;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.ltech.transaction_money.authorization.AuthorizationRequest.Params;
import com.ltech.transaction_money.transaction.Transaction;

@Service
public class AuthorizerService {

    private RestClient restClient;

    public AuthorizerService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.random.org/json-rpc/4/invoke")
                .build();
    }

    public void authorizeTransaction(Transaction transaction) {

        Params params = new Params("64680eaa-5a9b-4f40-b250-82c89b78a2d2", 1, 0, 1);
        AuthorizationRequest requestBody = new AuthorizationRequest("2.0", "generateIntegers", params, 1);

        var response = restClient.post()
                .body(requestBody)
                .retrieve()
                .toEntity(AuthorizationResponse.class);

        if (response.getStatusCode().isError() || !response.getBody().isAuthorized()) {
            throw new UnauthorizedTransactionException("Transaction not authorized");
        }

    }
}
