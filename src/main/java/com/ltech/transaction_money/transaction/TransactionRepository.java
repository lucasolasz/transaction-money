package com.ltech.transaction_money.transaction;

import org.springframework.data.repository.ListCrudRepository;

public interface TransactionRepository extends ListCrudRepository<Transaction, Long> {
    // Additional query methods can be defined here if needed
    
}
