package com.example.cornerstone_project_Aman.Wallet.service;

import com.example.cornerstone_project_Aman.Auth.config.JwtUtil;
import com.example.cornerstone_project_Aman.Transactions.entity.Transactions;
import com.example.cornerstone_project_Aman.Transactions.repository.TransactionsRepository;
import com.example.cornerstone_project_Aman.Wallet.entity.Wallet;
import com.example.cornerstone_project_Aman.Wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionsRepository transactionRepository;

    private JwtUtil jwtUtil;

    // Fetch wallet for authenticated user using JWT token
    public Wallet getWalletFromToken(String jwtToken) {
        // Extract userId from JWT token
        Long userId = jwtUtil.extractUserId(jwtToken);


        Wallet userWallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found for this user"));

        return userWallet;
    }

    // Fetch transactions for authenticated user's wallet using JWT token
    public List<Transactions> getTransactionsFromToken(String jwtToken) {
        // Get wallet using JWT token
        Wallet wallet = getWalletFromToken(jwtToken);

        // Fetch transactions associated with this wallet
        return transactionRepository.findByWalletId(wallet.getId());
    }
}



