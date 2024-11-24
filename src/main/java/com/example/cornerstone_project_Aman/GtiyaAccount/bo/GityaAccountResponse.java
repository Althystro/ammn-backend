package com.example.cornerstone_project_Aman.GtiyaAccount.bo;

import com.example.cornerstone_project_Aman.GtiyaAccount.entity.GityaAccount;

public class GityaAccountResponse {
    private Long id;
    private Double jointAccountBalance;
    private Long inviteCode;
    private String username;
    private int numberOfUsers;
    private double remainingBalance;

    public GityaAccountResponse(GityaAccount account) {
        this.id = account.getId();
        this.jointAccountBalance = account.getJointAccountBalance();
        this.inviteCode = account.getInviteCode();
        this.username = account.getUsername();
        this.numberOfUsers = account.getNumberOfUsers();
        this.remainingBalance = account.getRemainingBalance();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getJointAccountBalance() {
        return jointAccountBalance;
    }

    public void setJointAccountBalance(Double jointAccountBalance) {
        this.jointAccountBalance = jointAccountBalance;
    }

    public Long getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(Long inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }
}
