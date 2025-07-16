package com.ltech.transaction_money.authorization;

public record AuthorizationRequest(
        String jsonrpc,
        String method,
        Params params,
        int id) {

    public record Params(
            String apiKey,
            int n,
            int min,
            int max) {
    }

    public AuthorizationRequest generateInterBody() {
        Params params = new Params("64680eaa-5a9b-4f40-b250-82c89b78a2d2", 1, 0, 1);
        return new AuthorizationRequest("2.0", "generateIntegers", params, 1);
    }
}
