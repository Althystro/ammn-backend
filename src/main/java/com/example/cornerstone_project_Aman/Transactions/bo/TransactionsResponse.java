package com.example.cornerstone_project_Aman.Transactions.bo;


import com.example.cornerstone_project_Aman.Transactions.entity.TransactionType;

import java.util.Date;

public class TransactionsResponse {
    private TransactionType type;
    private double amount;
    private Date transactionDate;
    private Long walletId;


    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }
}
