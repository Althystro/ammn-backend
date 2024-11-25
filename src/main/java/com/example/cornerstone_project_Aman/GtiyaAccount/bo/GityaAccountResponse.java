package com.example.cornerstone_project_Aman.GtiyaAccount.bo;

import com.example.cornerstone_project_Aman.GtiyaAccount.entity.GityaAccount;

import java.util.List;
import java.util.stream.Collectors;

public class GityaAccountResponse {
    private Long id;
    private Double jointAccountBalance;
    private Long inviteCode;
    private String accountName;
    private int numberOfUsers;
    private List<UsersInAccount> users;
    private double remainingBalance;

    public GityaAccountResponse(GityaAccount account) {
        this.id = account.getId();
        this.jointAccountBalance = account.getJointAccountBalance();
        this.inviteCode = account.getInviteCode();
        this.accountName = account.getAccountName();
        this.numberOfUsers = account.getNumberOfUsers();
        this.remainingBalance = account.getRemainingBalance();

        this.users = account.getUsers().stream()
                .map(UsersInAccount::new)
                .collect(Collectors.toList());
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

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    public List<UsersInAccount> getUsers() {
        return users;
    }

    public void setUsers(List<UsersInAccount> users) {
        this.users = users;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }
}