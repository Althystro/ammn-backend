package com.example.cornerstone_project_Aman.GtiyaAccount.bo;

public class GityaAccountRequest {
    private Double jointAccountBalance;
    private Long inviteCode;
    private String username;
    private int numberOfUsers;

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

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
