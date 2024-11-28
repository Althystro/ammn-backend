package com.example.cornerstone_project_Aman.Transactions.repository;

import com.example.cornerstone_project_Aman.Transactions.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
    List<Transactions> findByWalletId(Long walletId);

    List<Transactions> findByWallet_UserId(Long userId);

    @Query("SELECT t FROM Transactions t WHERE t.amount = :amount AND t.transactionDate = :transactionDate")
    Optional<Transactions> findByAmountAndTransactionDate(double amount, String transactionDate);

}

