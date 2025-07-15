package com.ltech.transaction_money.wallet;

import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet, Long> {
    // Additional query methods can be defined here if needed
    
}
