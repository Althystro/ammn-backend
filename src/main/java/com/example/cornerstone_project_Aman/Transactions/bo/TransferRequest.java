package com.example.cornerstone_project_Aman.Transactions.bo;


import com.example.cornerstone_project_Aman.Transactions.entity.TransactionType;
import com.example.cornerstone_project_Aman.Wallet.entity.Wallet;

import java.util.Date;

public class TransferRequest {
    private TransactionType type;
    private double amount;
    private String email;
    private Date transactionDate;
    private Wallet wallet;

    // Getters and setters


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

}
