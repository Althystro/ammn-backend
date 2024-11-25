package com.example.cornerstone_project_Aman.GtiyaAccount.bo;

public class AddUserToGityaAccountRequest {
    private String email;
    private String accountName;
    private Long inviteCode;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
