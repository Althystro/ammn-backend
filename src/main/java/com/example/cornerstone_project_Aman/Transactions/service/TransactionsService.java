package com.example.cornerstone_project_Aman.Transactions.service;

import com.example.cornerstone_project_Aman.Transactions.bo.TransactionsRequest;
import com.example.cornerstone_project_Aman.Transactions.bo.TransactionsResponse;
import com.example.cornerstone_project_Aman.Transactions.entity.TransactionType;
import com.example.cornerstone_project_Aman.Transactions.entity.Transactions;
import com.example.cornerstone_project_Aman.Transactions.repository.TransactionsRepository;
import com.example.cornerstone_project_Aman.Users.entity.User;
import com.example.cornerstone_project_Aman.Wallet.entity.Wallet;
import com.example.cornerstone_project_Aman.Wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionsService {
    private final Date date = new Date(); // Initializes with the current date and time


    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private WalletRepository walletRepository;


    public List<Transactions> getUserTransactions(Long userId) {
        return transactionsRepository.findByWallet_UserId(userId);
    }

    //
    public TransactionsResponse depositAccount(TransactionsRequest transactionsRequest) {
        // Retrieve authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // Retrieve user's wallet and transactions
        Wallet userWallet = currentUser.getWallet();
        List<Transactions> walletTransactions = userWallet.getTransactions();

        // Validate input
        if (transactionsRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive.");
        }

        // Create a new deposit transaction
        Transactions depositTransaction = new Transactions();
        depositTransaction.setTransactionDate(date);
        depositTransaction.setType(TransactionType.Deposit);
        depositTransaction.setAmount(transactionsRequest.getAmount());
        // Update wallet balance
        userWallet.setBalance(userWallet.getBalance() + depositTransaction.getAmount());
        depositTransaction.setWallet(userWallet);

        // Add transaction to wallet's transaction list
        walletTransactions.add(depositTransaction);

        // Persist changes (assuming repositories are injected)
        transactionsRepository.save(depositTransaction);
        walletRepository.save(userWallet);

        // Create and return response
        TransactionsResponse response = new TransactionsResponse();
        response.setWalletId(depositTransaction.getId());
        response.setAmount(userWallet.getBalance());
        response.setType(TransactionType.Withdraw);
        response.setTransactionDate(date);
        return response;
    }

    public TransactionsResponse withdrawAccount(TransactionsRequest transactionsRequest) {
        // Retrieve authenticated user
        // Retrieve authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // Retrieve user's wallet and transactions
        Wallet userWallet = currentUser.getWallet();
        List<Transactions> walletTransactions = userWallet.getTransactions();

        // Validate input
        if (transactionsRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive.");
        }

        // Create a new deposit transaction
        Transactions withdrawTransaction = new Transactions();
        withdrawTransaction.setTransactionDate(date);
        withdrawTransaction.setType(TransactionType.Withdraw);
        withdrawTransaction.setAmount(transactionsRequest.getAmount());
        // Update wallet balance
        userWallet.setBalance(userWallet.getBalance() - withdrawTransaction.getAmount());
        withdrawTransaction.setWallet(userWallet);

        // Add transaction to wallet's transaction list
        walletTransactions.add(withdrawTransaction);

        // Persist changes (assuming repositories are injected)
        transactionsRepository.save(withdrawTransaction);
        walletRepository.save(userWallet);

        // Create and return response
        TransactionsResponse response = new TransactionsResponse();
        response.setWalletId(withdrawTransaction.getId());
        response.setAmount(transactionsRequest.getAmount());
        response.setType(TransactionType.Withdraw);
        response.setTransactionDate(date);
        return response;
    }


//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) authentication.getPrincipal();
//        List<Transactions> walletTransaction = currentUser.getWallet().getTransactions();
//        Transactions transactions = new Transactions();
//        transactions.setType(TransactionType.Deposit);
//        transactions.setAmount(transactionsRequest.getAmount());
//        transactions.setTransactionDate(date);
//        transactionsRepository.
//        walletTransaction.add(transactions);
//        return transactions;
//
//
//        wallet.setId(transactionsRequest.);
//        wallet.setUser(userWallet.getUser());
//        wallet.setBalance(userWallet.getBalance());
//        wallet.setTransactions(userWallet.getTransactions());
//
//        Transactions firstTransaction = new Transactions();
//        firstTransaction.setAmount(30);
//        firstTransaction.setType(TransactionType.Deposit);
//        List<Transactions> transactionsList = new ArrayList<>();
//        transactionsList.add(firstTransaction);
//        wallet.setTransactions(transactionsList);
//

//
//    @Autowired
//    private WalletRepository walletRepository;
//
//    @Autowired
//    private TransactionsRepository transactionRepository;
//
//    @Autowired
//    private AuthenticationService authenticationService;
//
//    public List<Transactions> getAuthenticatedUserTransactions(LoginUserRequest loginUserRequest) {
//        User authenticatedUser = authenticationService.authenticate(loginUserRequest);
//        Long userId = authenticatedUser.getId();
//
//        // Fetch wallet associated with the user
//        Wallet wallet = walletRepository.findByUserId(userId)
//                .orElseThrow(() -> new IllegalArgumentException("Wallet not found for this user"));
//
//        // Fetch transactions associated with the wallet
//        return transactionRepository.findByWalletId(wallet.getId());
//    }
}

