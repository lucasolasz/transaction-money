package com.ltech.transaction_money.authorization;

public record AuthorizationRequest(  
    String jsonrpc,
    String method,
    Params params,
    int id
    ) {
        
    public record Params(
        String apiKey,
        int n,
        int min,
        int max
    ) {}
}
