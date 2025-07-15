package com.ltech.transaction_money.transaction;

public class InvalidTransactionException extends RuntimeException {
   public InvalidTransactionException(String message) {
    super(message);
  }

}
