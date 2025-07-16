package com.ltech.transaction_money.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthorizationResponse(@JsonProperty("data") int[] data) {
    
    public boolean isAuthorized() {
        return data != null && data.length > 0 && data[0] == 1;
    }
}
