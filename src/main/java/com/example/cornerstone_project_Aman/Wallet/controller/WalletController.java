package com.example.cornerstone_project_Aman.Wallet.controller;

import com.example.cornerstone_project_Aman.Users.entity.User;
import com.example.cornerstone_project_Aman.Wallet.entity.Wallet;
import com.example.cornerstone_project_Aman.Wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

    @Autowired
    private WalletService walletService;

    // Endpoint to get wallet details for authenticated user
    @GetMapping("/wallet")
    public ResponseEntity<Wallet> getWallet() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Wallet userWallet = currentUser.getWallet();

//        WalletResponse wallet = new WalletResponse();
//        wallet.setId(userWallet.getId());
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

        return ResponseEntity.ok(userWallet);
    }


}
