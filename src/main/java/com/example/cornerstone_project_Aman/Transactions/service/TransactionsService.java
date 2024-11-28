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

        // Create sender transaction
        Transactions senderTransaction = new Transactions();
        senderTransaction.setTransactionDate(new Date());
        senderTransaction.setType(TransactionType.Taslefa);
        senderTransaction.setAmount(salfniRequest.getAmount());
        senderTransaction.setWallet(senderWallet);
        senderTransaction.setFirstName(recipientUser.getFirstName()); // Add recipient's first name
        senderTransaction.setLastName(recipientUser.getLastName());   // Add recipient's last name
        senderTransaction.setUserEmail(recipientUser.getEmail());
        senderTransaction.setTaslefaStatus(TransactionType.Incomplete);
        senderWallet.setBalance(senderWallet.getBalance() - salfniRequest.getAmount());
        senderTransactions.add(senderTransaction);

        Transactions recipientTransaction = new Transactions();
        recipientTransaction.setTransactionDate(new Date());
        recipientTransaction.setType(TransactionType.TaslefaTo);
        recipientTransaction.setAmount(salfniRequest.getAmount());
        recipientTransaction.setWallet(recipientWallet);
        recipientTransaction.setFirstName(currentUser.getFirstName()); // Add sender's first name
        recipientTransaction.setLastName(currentUser.getLastName());   // Add sender's last name
        recipientTransaction.setUserEmail(currentUser.getEmail());
        recipientTransaction.setTaslefaStatus(TransactionType.Incomplete);

        recipientWallet.setBalance(recipientWallet.getBalance() + salfniRequest.getAmount());
        recipientTransactions.add(recipientTransaction);

        // Save transactions and wallets
        transactionsRepository.save(senderTransaction);
        transactionsRepository.save(recipientTransaction);

        walletRepository.save(senderWallet);
        walletRepository.save(recipientWallet);

        // Prepare response
        TransactionsResponse response = new TransactionsResponse();
        response.setAmount(salfniRequest.getAmount());
        response.setType(TransactionType.Taslefa);
        response.setTransactionDate(senderTransaction.getTransactionDate());
        response.setFirstName(recipientUser.getFirstName());
        response.setLastName(recipientUser.getLastName());
        response.setUserEmail(recipientUser.getEmail());
        response.setWalletId(senderWallet.getId());

        return response;
    }

    public TransactionsResponse salfniReturn(TransferRequest salfniRequest) {
        // Get the  user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Wallet recipientWallet = currentUser.getWallet();
        if (recipientWallet == null) {
            throw new IllegalArgumentException("Recipient wallet not found.");
        }

        List<Transactions> recipientTransactions = recipientWallet.getTransactions();

        // Find the original transaction in the recipient's transactions
        Transactions originalTransaction = recipientTransactions.stream()
                .filter(transaction -> transaction.getId().equals(salfniRequest.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Original transaction not found."));

        // Reverse the transaction: deduct from recipient and add back to sender
        double amountToBeReturned = originalTransaction.getAmount();
        // Ensure there are sufficient funds in the recipient's wallet
        if (recipientWallet.getBalance() < amountToBeReturned) {
            throw new IllegalArgumentException("Insufficient balance in recipient's wallet.");
        }

        // Deduct amount from recipient
        recipientWallet.setBalance(recipientWallet.getBalance() - amountToBeReturned);
        walletRepository.save(recipientWallet);

        // Find the original sender using their email from the transaction
        User senderUser = userService.findByEmailString(originalTransaction.getUserEmail());
        if (senderUser == null) {
            throw new IllegalArgumentException("Sender not found with the provided email.");
        }

        Wallet senderWallet = senderUser.getWallet();
        if (senderWallet == null) {
            throw new IllegalArgumentException("Sender wallet not found.");
        }

        // Add amount back to sender's wallet
        senderWallet.setBalance(senderWallet.getBalance() + amountToBeReturned);
        walletRepository.save(senderWallet);

        // Create a new transaction for sender indicating return
        Transactions returnTransactionForSender = new Transactions();
        returnTransactionForSender.setTransactionDate(new Date());
        returnTransactionForSender.setType(TransactionType.TaslefaReturned);
        returnTransactionForSender.setAmount(amountToBeReturned);
        returnTransactionForSender.setWallet(senderWallet);
        returnTransactionForSender.setFirstName(currentUser.getFirstName());
        returnTransactionForSender.setLastName(currentUser.getLastName());
        returnTransactionForSender.setUserEmail(currentUser.getEmail());
        returnTransactionForSender.setTaslefaStatus(TransactionType.Complete);

        // Create a new transaction for recipient indicating deduction
        Transactions deductionTransactionForRecipient = new Transactions();
        deductionTransactionForRecipient.setTransactionDate(new Date());
        deductionTransactionForRecipient.setType(TransactionType.TaslefaReturned);
        deductionTransactionForRecipient.setAmount(amountToBeReturned);
        deductionTransactionForRecipient.setWallet(recipientWallet);
        deductionTransactionForRecipient.setFirstName(senderUser.getFirstName());
        deductionTransactionForRecipient.setLastName(senderUser.getLastName());
        deductionTransactionForRecipient.setUserEmail(senderUser.getEmail());
        deductionTransactionForRecipient.setTaslefaStatus(TransactionType.Complete);

        // Save transactions and wallets
        transactionsRepository.save(returnTransactionForSender);
        transactionsRepository.save(deductionTransactionForRecipient);


        // Prepare response
        TransactionsResponse response = new TransactionsResponse();
        response.setAmount(amountToBeReturned);
        response.setType(TransactionType.TaslefaReturned);
        response.setTransactionDate(returnTransactionForSender.getTransactionDate());
        response.setFirstName(senderUser.getFirstName());
        response.setLastName(senderUser.getLastName());
        response.setUserEmail(senderUser.getEmail());
        response.setWalletId(senderWallet.getId());
        response.setTaslefaStatus(TransactionType.Complete);

        return response;
    }

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

    @Transactional
    public void completeTransaction(TransactionsRequest request) {
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // Get the sender's wallet and transaction list
        Wallet senderWallet = currentUser.getWallet();
        List<Transactions> senderTransactions = senderWallet.getTransactions();

        // Find the sender's transaction by ID
        Transactions senderTransaction = senderTransactions.stream()
                .filter(transaction -> transaction.getId().toString().equals(request.getId().toString()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found for the current user"));

        // Ensure the transaction is incomplete and of type Taslefa or TaslefaTo
        if (!"Incomplete".equals(senderTransaction.getTaslefaStatus().toString())) {
            throw new IllegalStateException("Transaction is already completed");
        }

        if (!TransactionType.Taslefa.equals(senderTransaction.getType()) &&
                !TransactionType.TaslefaTo.equals(senderTransaction.getType())) {
            throw new IllegalArgumentException("Invalid transaction type for completion");
        }

        // Update sender's transaction details
        senderTransaction.setTaslefaStatus(TransactionType.Complete);
        senderTransaction.setType(TransactionType.TaslefaReturned);

        // Find the recipient using their email from the sender's transaction
        String recipientEmail = senderTransaction.getUserEmail();
        User recipientUser = userService.findByEmailString(recipientEmail);
        if (recipientUser == null) {
            throw new IllegalArgumentException("Recipient not found with the provided email.");
        }

        // Access the recipient's wallet
        Wallet recipientWallet = recipientUser.getWallet();
        if (recipientWallet == null) {
            throw new IllegalArgumentException("Recipient wallet not found.");
        }

        // Find the recipient's corresponding transaction using amount and transaction date
        Transactions recipientTransaction = recipientWallet.getTransactions().stream()
                .filter(transaction ->
                        transaction.getAmount() == senderTransaction.getAmount() &&
                                transaction.getTransactionDate().equals(senderTransaction.getTransactionDate()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Recipient transaction not found."));

        // Update recipient's transaction details
        recipientTransaction.setTaslefaStatus(TransactionType.Complete);
        recipientTransaction.setType(TransactionType.TaslefaReturned);

        // Transfer funds: Add back to sender and deduct from recipient
        double amount = senderTransaction.getAmount();
        senderWallet.setBalance(senderWallet.getBalance() - amount);
        recipientWallet.setBalance(recipientWallet.getBalance() + amount);

        // Save updated transactions and wallets
        transactionsRepository.save(senderTransaction);
        transactionsRepository.save(recipientTransaction);
        walletRepository.save(senderWallet);
        walletRepository.save(recipientWallet);
    }
}


