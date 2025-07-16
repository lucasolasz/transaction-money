package com.ltech.transaction_money.transaction;

import org.springframework.stereotype.Service;

import com.ltech.transaction_money.authorization.AuthorizerService;
import com.ltech.transaction_money.notification.NotificationService;
import com.ltech.transaction_money.wallet.Wallet;
import com.ltech.transaction_money.wallet.WalletRepository;
import com.ltech.transaction_money.wallet.WalletType;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final AuthorizerService authorizerService;
    private final NotificationService notificationService;

    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository,
            AuthorizerService authorizerService, NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.authorizerService = authorizerService;
        this.notificationService = notificationService;
    }

    public Transaction createTransaction(Transaction transaction) {
        // Validar
        this.validate(transaction);

        // Criar a transação
        var newTransaction = transactionRepository.save(transaction);

        // Debitar da carteira
        var walletPayer = walletRepository.findById(transaction.payer()).get();
        var walletPayee = walletRepository.findById(transaction.payee()).get();
        walletRepository.save(walletPayer.debit(transaction.value()));
        walletRepository.save(walletPayee.credit(transaction.value()));

        // Chamar serviços externos
        authorizerService.authorizeTransaction(newTransaction);
        notificationService.notifyTransaction(transaction);

        return newTransaction;
    }

    private void validate(Transaction transaction) {

        walletRepository.findById(transaction.payee())
                .map(payee -> walletRepository.findById(transaction.payer())
                        .map(payer -> isTransactionValid(transaction, payer) ? true : null)
                        .orElseThrow(() -> new InvalidTransactionException("Invalid transaction - " + transaction)))
                .orElseThrow(() -> new InvalidTransactionException(
                        "Invalid transaction - " + transaction));
    }

    private boolean isTransactionValid(Transaction transaction, Wallet payer) {
        return payer.type() == WalletType.COMUM.getValue() &&
                payer.balance().compareTo(transaction.value()) >= 0 &&
                !payer.id().equals(transaction.payee());
    }
}
