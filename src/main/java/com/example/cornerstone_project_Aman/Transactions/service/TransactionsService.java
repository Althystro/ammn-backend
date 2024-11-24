package com.example.cornerstone_project_Aman.Transactions.service;

import com.example.cornerstone_project_Aman.Transactions.bo.TransactionsRequest;
import com.example.cornerstone_project_Aman.Transactions.entity.TransactionType;
import com.example.cornerstone_project_Aman.Transactions.entity.Transactions;
import com.example.cornerstone_project_Aman.Transactions.repository.TransactionsRepository;
import com.example.cornerstone_project_Aman.Users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionsService {
    Date date;

    @Autowired
    private TransactionsRepository transactionsRepository;

    public List<Transactions> getUserTransactions(Long userId) {
        return transactionsRepository.findByWallet_UserId(userId);
    }


    public Transactions depositAccount(TransactionsRequest transactionsRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        List<Transactions> walletTransaction = currentUser.getWallet().getTransactions();
        Transactions transactions = new Transactions();
        transactions.setType(TransactionType.Deposit);
        transactions.setAmount(transactionsRequest.getAmount());
        transactions.setTransactionDate(date);
//        transactionsRepository
        walletTransaction.add(transactions);
//something
        return transactions;
        // walletTransaction.add(transactionsRequest);


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


    }


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

