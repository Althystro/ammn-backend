package com.example.cornerstone_project_Aman.Transactions.bo;

public class FundsRequest {
    private String accountName;
    private double amount;

    // Getters and Setters
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}