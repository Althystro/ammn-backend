package com.example.cornerstone_project_Aman.Transactions.service;

import com.example.cornerstone_project_Aman.GtiyaAccount.entity.GityaAccount;
import com.example.cornerstone_project_Aman.GtiyaAccount.repository.GityaAccountRepository;
import com.example.cornerstone_project_Aman.Transactions.bo.*;
import com.example.cornerstone_project_Aman.Transactions.entity.TransactionType;
import com.example.cornerstone_project_Aman.Transactions.entity.Transactions;
import com.example.cornerstone_project_Aman.Transactions.repository.TransactionsRepository;
import com.example.cornerstone_project_Aman.Users.entity.User;
import com.example.cornerstone_project_Aman.Users.repository.UserRepository;
import com.example.cornerstone_project_Aman.Users.service.UserService;
import com.example.cornerstone_project_Aman.Wallet.entity.Wallet;
import com.example.cornerstone_project_Aman.Wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
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
    GityaAccountRepository accountRepository;
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

    public TransactionsResponse depositAccount(TransactionsRequest transactionsRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Wallet userWallet = currentUser.getWallet();
        List<Transactions> walletTransactions = userWallet.getTransactions();

        if (transactionsRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive.");
        }

        Transactions depositTransaction = new Transactions();
        depositTransaction.setTransactionDate(date);
        depositTransaction.setType(TransactionType.Deposit);
        depositTransaction.setAmount(transactionsRequest.getAmount());
        userWallet.setBalance(userWallet.getBalance() + depositTransaction.getAmount());
        depositTransaction.setWallet(userWallet);

        walletTransactions.add(depositTransaction);

        transactionsRepository.save(depositTransaction);
        walletRepository.save(userWallet);

        TransactionsResponse response = new TransactionsResponse();
        response.setWalletId(depositTransaction.getId());
        response.setAmount(transactionsRequest.getAmount());
        response.setType(TransactionType.Deposit);
        response.setTransactionDate(date);
        return response;
    }

    public TransactionsResponse withdrawAccount(TransactionsRequest transactionsRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Wallet userWallet = currentUser.getWallet();
        List<Transactions> walletTransactions = userWallet.getTransactions();

        if (transactionsRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive.");
        }

        Transactions withdrawTransaction = new Transactions();
        withdrawTransaction.setTransactionDate(date);
        withdrawTransaction.setType(TransactionType.Withdraw);
        withdrawTransaction.setAmount(transactionsRequest.getAmount());
        userWallet.setBalance(userWallet.getBalance() - withdrawTransaction.getAmount());
        withdrawTransaction.setWallet(userWallet);

        walletTransactions.add(withdrawTransaction);

        transactionsRepository.save(withdrawTransaction);
        walletRepository.save(userWallet);

        TransactionsResponse response = new TransactionsResponse();
        response.setWalletId(withdrawTransaction.getId());
        response.setAmount(transactionsRequest.getAmount());
        response.setType(TransactionType.Withdraw);
        response.setTransactionDate(date);
        return response;
    }


    public TransactionsResponse transferToAccount(TransferRequest transferRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Wallet senderWallet = currentUser.getWallet();
        List<Transactions> senderTransactions = senderWallet.getTransactions();

        if (transferRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive.");
        }

        if (senderWallet.getBalance() < transferRequest.getAmount()) {
            throw new IllegalArgumentException("Insufficient balance for transfer.");
        }


        User recipientUser = userService.findByEmailString(transferRequest.getEmail());
        if (recipientUser == null) {
            throw new IllegalArgumentException("Recipient not found with the provided email.");
        }

        Wallet recipientWallet = recipientUser.getWallet();
        if (recipientWallet == null) {
            throw new IllegalArgumentException("Recipient wallet not found.");
        }

        List<Transactions> recipientTransactions = recipientWallet.getTransactions();

        Transactions senderTransaction = new Transactions();
        senderTransaction.setTransactionDate(date);
        senderTransaction.setType(TransactionType.TransferFrom);
        senderTransaction.setAmount(transferRequest.getAmount());
        senderTransaction.setWallet(senderWallet);

        senderWallet.setBalance(senderWallet.getBalance() - transferRequest.getAmount());
        senderTransactions.add(senderTransaction);

        Transactions recipientTransaction = new Transactions();
        recipientTransaction.setTransactionDate(date);
        recipientTransaction.setType(TransactionType.TransferTo);
        recipientTransaction.setAmount(transferRequest.getAmount());
        recipientTransaction.setWallet(recipientWallet);

        recipientWallet.setBalance(recipientWallet.getBalance() + transferRequest.getAmount());
        recipientTransactions.add(recipientTransaction);

        transactionsRepository.save(senderTransaction);
        transactionsRepository.save(recipientTransaction);

        walletRepository.save(senderWallet);
        walletRepository.save(recipientWallet);

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

        Wallet senderWallet = currentUser.getWallet();
        List<Transactions> senderTransactions = senderWallet.getTransactions();

        if (salfniRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive.");
        }

        if (senderWallet.getBalance() < salfniRequest.getAmount()) {
            throw new IllegalArgumentException("Insufficient balance for transfer.");
        }

        User recipientUser = userService.findByEmailString(salfniRequest.getEmail());
        if (recipientUser == null) {
            throw new IllegalArgumentException("Recipient not found with the provided email.");
        }

        Wallet recipientWallet = recipientUser.getWallet();
        if (recipientWallet == null) {
            throw new IllegalArgumentException("Recipient wallet not found.");
        }

        List<Transactions> recipientTransactions = recipientWallet.getTransactions();

        Transactions senderTransaction = new Transactions();
        senderTransaction.setTransactionDate(date);
        senderTransaction.setType(TransactionType.Taslefa);
        senderTransaction.setAmount(salfniRequest.getAmount());
        senderTransaction.setWallet(senderWallet);

        senderWallet.setBalance(senderWallet.getBalance() - salfniRequest.getAmount());
        senderTransactions.add(senderTransaction);

        Transactions recipientTransaction = new Transactions();
        recipientTransaction.setTransactionDate(date);
        recipientTransaction.setType(TransactionType.Taslefa);
        recipientTransaction.setAmount(salfniRequest.getAmount());
        recipientTransaction.setWallet(recipientWallet);

        recipientWallet.setBalance(recipientWallet.getBalance() + salfniRequest.getAmount());
        recipientTransactions.add(recipientTransaction);

        transactionsRepository.save(senderTransaction);
        transactionsRepository.save(recipientTransaction);

        walletRepository.save(senderWallet);
        walletRepository.save(recipientWallet);

        TransactionsResponse response = new TransactionsResponse();
        response.setAmount(salfniRequest.getAmount());
        response.setType(TransactionType.Taslefa);
        response.setTransactionDate(recipientTransaction.getTransactionDate());
        response.setWalletId(senderTransaction.getWallet().getId());

        return response;
    }

//    @Transactional
//    public void addFundsToGityaAccount(FundsRequest request) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User authenticatedUser = (User) authentication.getPrincipal();
//
//        Wallet userWallet = authenticatedUser.getWallet();
//        if (userWallet == null) {
//            throw new RuntimeException("Wallet not found for the authenticated user.");
//        }
//
//        if (userWallet.getBalance() < request.getAmount()) {
//            throw new RuntimeException("Insufficient balance in wallet.");
//        }
//
//        GityaAccount gityaAccount = authenticatedUser.getGityaAccountList().stream()
//                .filter(account -> account.getAccountName().equalsIgnoreCase(request.getAccountName()))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Gitya account not found or does not belong to the user."));
//
//        userWallet.setBalance(userWallet.getBalance() + request.getAmount());
//        gityaAccount.setJointAccountBalance(gityaAccount.getJointAccountBalance() + request.getAmount());
//
//        walletRepository.save(userWallet);
//        accountRepository.save(gityaAccount);
//    }


    //    @Transactional
//    public void takeFundsfromGityaAccount(FundsRequest request) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User authenticatedUser = (User) authentication.getPrincipal();
//
//        Wallet userWallet = authenticatedUser.getWallet();
//        if (userWallet == null) {
//            throw new RuntimeException("Wallet not found for the authenticated user.");
//        }
//
//        if (userWallet.getBalance() < request.getAmount()) {
//            throw new RuntimeException("Insufficient balance in wallet.");
//        }
//
//        GityaAccount gityaAccount = authenticatedUser.getGityaAccountList().stream()
//                .filter(account -> account.getAccountName().equalsIgnoreCase(request.getAccountName()))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Gitya account not found or does not belong to the user."));
//
//        userWallet.setBalance(userWallet.getBalance() - request.getAmount());
//        gityaAccount.setJointAccountBalance(gityaAccount.getJointAccountBalance() + request.getAmount());
//
//        // Save changes
//        walletRepository.save(userWallet);
//        accountRepository.save(gityaAccount);
//    }
//
//    @Transactional
//    public void withdrawFundsFromGityaAccount(FundsRequest request) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User authenticatedUser = (User) authentication.getPrincipal();
//
//        Wallet userWallet = authenticatedUser.getWallet();
//        if (userWallet == null) {
//            throw new RuntimeException("Wallet not found for the authenticated user.");
//        }
//
//        GityaAccount gityaAccount = authenticatedUser.getGityaAccountList().stream()
//                .filter(account -> account.getAccountName().equalsIgnoreCase(request.getAccountName()))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Gitya account not found or does not belong to the user."));
//
//        if (gityaAccount.getJointAccountBalance() < request.getAmount()) {
//            throw new RuntimeException("Insufficient balance in Gitya account.");
//        }
//
//        gityaAccount.setJointAccountBalance(gityaAccount.getJointAccountBalance() - request.getAmount());
//        userWallet.setBalance(userWallet.getBalance() + request.getAmount());
//
//        accountRepository.save(gityaAccount);
//        walletRepository.save(userWallet);
//    }
    @Transactional
    public TransferResponse addFundsToGityaAccount(FundsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        Wallet userWallet = authenticatedUser.getWallet();
        if (userWallet == null) {
            throw new RuntimeException("Wallet not found for the authenticated user.");
        }

        if (userWallet.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient balance in wallet.");
        }

        GityaAccount gityaAccount = authenticatedUser.getGityaAccountList().stream()
                .filter(account -> account.getAccountName().equalsIgnoreCase(request.getAccountName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Gitya account not found or does not belong to the user."));

        userWallet.setBalance(userWallet.getBalance() - request.getAmount());
        gityaAccount.setJointAccountBalance(gityaAccount.getJointAccountBalance() + request.getAmount());

        walletRepository.save(userWallet);
        accountRepository.save(gityaAccount);

        Transactions transaction = new Transactions();
        transaction.setType(TransactionType.Transfer);
        transaction.setAmount(request.getAmount());
        transaction.setTransactionDate(new Date());
        transaction.setWallet(userWallet);

        transactionsRepository.save(transaction);

        TransferResponse response = new TransferResponse();
        response.setType(TransactionType.Transfer);
        response.setAmount(request.getAmount());
        response.setReceiverId(gityaAccount.getId());
        response.setTransactionDate(new Date());

        return response;
    }

    @Transactional
    public TransferResponse takeFundsfromGityaAccount(FundsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        Wallet userWallet = authenticatedUser.getWallet();
        if (userWallet == null) {
            throw new RuntimeException("Wallet not found for the authenticated user.");
        }

        if (userWallet.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient balance in wallet.");
        }

        GityaAccount gityaAccount = authenticatedUser.getGityaAccountList().stream()
                .filter(account -> account.getAccountName().equalsIgnoreCase(request.getAccountName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Gitya account not found or does not belong to the user."));

        userWallet.setBalance(userWallet.getBalance() - request.getAmount());
        gityaAccount.setJointAccountBalance(gityaAccount.getJointAccountBalance() + request.getAmount());

        walletRepository.save(userWallet);
        accountRepository.save(gityaAccount);

        Transactions transaction = new Transactions();
        transaction.setType(TransactionType.Transfer);
        transaction.setAmount(request.getAmount());
        transaction.setTransactionDate(new Date());
        transaction.setWallet(userWallet);

        transactionsRepository.save(transaction);

        TransferResponse response = new TransferResponse();
        response.setType(TransactionType.Transfer);
        response.setAmount(request.getAmount());
        response.setReceiverId(gityaAccount.getId());
        response.setTransactionDate(new Date());

        return response;
    }


}

