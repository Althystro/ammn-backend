package com.example.cornerstone_project_Aman.Transactions.controller;

import com.example.cornerstone_project_Aman.Transactions.entity.Transactions;
import com.example.cornerstone_project_Aman.Transactions.service.TransactionsService;
import com.example.cornerstone_project_Aman.Users.entity.User;
import com.example.cornerstone_project_Aman.Wallet.entity.Wallet;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionsController {
    TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    //     Endpoint to get transactions for authenticated user's wallet
    @GetMapping("/wallet/transactions")
    public ResponseEntity<List<Transactions>> getTransactions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Wallet userWallet = currentUser.getWallet();
        List<Transactions> transactionsList = userWallet.getTransactions();
        return ResponseEntity.ok(transactionsList);

    }


//    @PostMapping("/wallet/transactions/deposit")
//    public ResponseEntity<TransactionsResponse> addNewTransaction(@RequestBody TransactionsRequest transactionsRequest) {
//
//        TransactionsResponse transactionsRequest1 = transactionsService.depositAccount(transactionsRequest);
//        return ResponseEntity.ok(transactionsRequest1);
//
//
//        //        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        User currentUser = (User) authentication.getPrincipal();
////        Wallet userWallet = currentUser.getWallet();
////        List<Transactions> transactionsList = userWallet.getTransactions();
////        transactionsList.add(transactionsRequest);
////        return ResponseEntity.ok(transactionsList);
//
//    }
}
