package com.example.cornerstone_project_Aman.Transactions.service;

import com.example.cornerstone_project_Aman.Transactions.bo.TransactionsRequest;
import com.example.cornerstone_project_Aman.Transactions.bo.TransactionsResponse;
import com.example.cornerstone_project_Aman.Transactions.bo.TransferRequest;
import com.example.cornerstone_project_Aman.Transactions.entity.TransactionType;
import com.example.cornerstone_project_Aman.Transactions.entity.Transactions;
import com.example.cornerstone_project_Aman.Transactions.repository.TransactionsRepository;
import com.example.cornerstone_project_Aman.Users.entity.User;
import com.example.cornerstone_project_Aman.Users.repository.UserRepository;
import com.example.cornerstone_project_Aman.Users.service.UserService;
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

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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
        response.setAmount(transactionsRequest.getAmount());
        response.setType(TransactionType.Deposit);
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

    //
    public TransactionsResponse transferToAccount(TransferRequest transferRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // Retrieve sender's wallet
        Wallet senderWallet = currentUser.getWallet();
        List<Transactions> senderTransactions = senderWallet.getTransactions();

        // Validate input
        if (transferRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive.");
        }

        if (senderWallet.getBalance() < transferRequest.getAmount()) {
            throw new IllegalArgumentException("Insufficient balance for transfer.");
        }

        // Find recipient by email

        User recipientUser = userService.findByEmailString(transferRequest.getEmail());
        if (recipientUser == null) {
            throw new IllegalArgumentException("Recipient not found with the provided email.");
        }

        Wallet recipientWallet = recipientUser.getWallet();
        if (recipientWallet == null) {
            throw new IllegalArgumentException("Recipient wallet not found.");
        }

        List<Transactions> recipientTransactions = recipientWallet.getTransactions();

        // Create a withdrawal transaction for the sender
        Transactions senderTransaction = new Transactions();
        senderTransaction.setTransactionDate(date);
        senderTransaction.setType(TransactionType.TransferFrom);
        senderTransaction.setAmount(transferRequest.getAmount());
        senderTransaction.setWallet(senderWallet);

        // Update sender's wallet balance
        senderWallet.setBalance(senderWallet.getBalance() - transferRequest.getAmount());
        senderTransactions.add(senderTransaction);

        // Create a deposit transaction for the recipient
        Transactions recipientTransaction = new Transactions();
        recipientTransaction.setTransactionDate(date);
        recipientTransaction.setType(TransactionType.TransferTo);
        recipientTransaction.setAmount(transferRequest.getAmount());
        recipientTransaction.setWallet(recipientWallet);

        // Update recipient's wallet balance
        recipientWallet.setBalance(recipientWallet.getBalance() + transferRequest.getAmount());
        recipientTransactions.add(recipientTransaction);

        // Persist changes
        transactionsRepository.save(senderTransaction);
        transactionsRepository.save(recipientTransaction);

        walletRepository.save(senderWallet);
        walletRepository.save(recipientWallet);

        // Create and return response
        TransactionsResponse response = new TransactionsResponse();
        response.setAmount(transferRequest.getAmount());
        response.setType(TransactionType.Transfer);
        response.setTransactionDate(recipientTransaction.getTransactionDate());
        response.setWalletId(senderTransaction.getWallet().getId());

        return response;
    }

    public TransactionsResponse salfniToAccount(TransferRequest salfniRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // Retrieve sender's wallet
        Wallet senderWallet = currentUser.getWallet();
        List<Transactions> senderTransactions = senderWallet.getTransactions();

        // Validate input
        if (salfniRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive.");
        }

        if (senderWallet.getBalance() < salfniRequest.getAmount()) {
            throw new IllegalArgumentException("Insufficient balance for transfer.");
        }

        // Find recipient by email

        User recipientUser = userService.findByEmailString(salfniRequest.getEmail());
        if (recipientUser == null) {
            throw new IllegalArgumentException("Recipient not found with the provided email.");
        }

        Wallet recipientWallet = recipientUser.getWallet();
        if (recipientWallet == null) {
            throw new IllegalArgumentException("Recipient wallet not found.");
        }

        List<Transactions> recipientTransactions = recipientWallet.getTransactions();

        // Create a withdrawal transaction for the sender
        Transactions senderTransaction = new Transactions();
        senderTransaction.setTransactionDate(date);
        senderTransaction.setType(TransactionType.Taslefa);
        senderTransaction.setAmount(salfniRequest.getAmount());
        senderTransaction.setWallet(senderWallet);

        // Update sender's wallet balance
        senderWallet.setBalance(senderWallet.getBalance() - salfniRequest.getAmount());
        senderTransactions.add(senderTransaction);

        // Create a deposit transaction for the recipient
        Transactions recipientTransaction = new Transactions();
        recipientTransaction.setTransactionDate(date);
        recipientTransaction.setType(TransactionType.Taslefa);
        recipientTransaction.setAmount(salfniRequest.getAmount());
        recipientTransaction.setWallet(recipientWallet);

        // Update recipient's wallet balance
        recipientWallet.setBalance(recipientWallet.getBalance() + salfniRequest.getAmount());
        recipientTransactions.add(recipientTransaction);

        // Persist changes
        transactionsRepository.save(senderTransaction);
        transactionsRepository.save(recipientTransaction);

        walletRepository.save(senderWallet);
        walletRepository.save(recipientWallet);

        // Create and return response
        TransactionsResponse response = new TransactionsResponse();
        response.setAmount(salfniRequest.getAmount());
        response.setType(TransactionType.Taslefa);
        response.setTransactionDate(recipientTransaction.getTransactionDate());
        response.setWalletId(senderTransaction.getWallet().getId());

        return response;
    }
}

