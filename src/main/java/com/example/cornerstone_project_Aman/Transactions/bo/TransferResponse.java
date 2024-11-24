package com.example.cornerstone_project_Aman.Transactions.bo;


import com.example.cornerstone_project_Aman.Transactions.entity.TransactionType;

import java.util.Date;

public class TransferResponse {
    private TransactionType type;
    private double amount;
    private Long receiverId;
    private Date transactionDate;

    // Getters and setters


    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
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
