package com.example.cornerstone_project_Aman.Transactions.controller;

import com.example.cornerstone_project_Aman.Transactions.bo.*;
import com.example.cornerstone_project_Aman.Transactions.entity.Transactions;
import com.example.cornerstone_project_Aman.Transactions.service.TransactionsService;
import com.example.cornerstone_project_Aman.Users.entity.User;
import com.example.cornerstone_project_Aman.Wallet.entity.Wallet;
import com.example.cornerstone_project_Aman.Wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionsController {
    private final WalletService walletService;
    TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService, WalletService walletService) {
        this.transactionsService = transactionsService;
        this.walletService = walletService;
    }

    @GetMapping("/wallet/transactions")
    public ResponseEntity<List<Transactions>> getTransactions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Wallet userWallet = currentUser.getWallet();
        List<Transactions> transactionsList = userWallet.getTransactions();
        return ResponseEntity.ok(transactionsList);

    }


    @PostMapping("/wallet/transactions/deposit")
    public ResponseEntity<TransactionsResponse> depositTransaction(@RequestBody TransactionsRequest transactionsRequest) {

        TransactionsResponse response = transactionsService.depositAccount(transactionsRequest);
        return ResponseEntity.ok(response);


    }

    @PostMapping("/wallet/transactions/withdraw")
    public ResponseEntity<TransactionsResponse> withdrawTransaction(@RequestBody TransactionsRequest transactionsRequest) {

        TransactionsResponse response = transactionsService.withdrawAccount(transactionsRequest);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/wallet/transactions/transfer")
    public ResponseEntity<TransactionsResponse> transferTransaction(@RequestBody TransferRequest transferRequest) {

        TransactionsResponse response = transactionsService.transferToAccount(transferRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/wallet/transactions/salfni")
    public ResponseEntity<TransactionsResponse> salfniTransaction(@RequestBody TransferRequest salfniRequest) {
        TransactionsResponse response = transactionsService.salfniToAccount(salfniRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/wallet/transactions/salfniReturned")
    public ResponseEntity<TransactionsResponse> salfniReturned(@RequestBody TransferRequest salfniRequest) {
        TransactionsResponse response = transactionsService.salfniReturn(salfniRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/wallet/addFundsToGityaAccount")
    public ResponseEntity<TransferResponse> addFundsToGityaAccount(@RequestBody FundsRequest request) {
        transactionsService.addFundsToGityaAccount(request);
        return ResponseEntity.ok(transactionsService.addFundsToGityaAccount(request));
    }

    @PostMapping("/wallet/takeFundsfromGityaAccount")
    public ResponseEntity<TransferResponse> takeFundsfromGityaAccount(@RequestBody FundsRequest request) {
        transactionsService.takeFundsfromGityaAccount(request);
        return ResponseEntity.ok(transactionsService.takeFundsfromGityaAccount(request));
    }

    @PutMapping("/wallet/transactions/completed")
    public ResponseEntity<String> completeTransaction(@RequestBody TransactionsRequest transactionsRequest) {
        // Get the authenticated user
        // Call the service method to complete the transaction
        transactionsService.completeTransaction(transactionsRequest);

        return ResponseEntity.ok("Transaction completed successfully.");
    }
}
