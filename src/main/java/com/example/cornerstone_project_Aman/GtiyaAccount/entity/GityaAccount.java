package com.example.cornerstone_project_Aman.GtiyaAccount.entity;

import com.example.cornerstone_project_Aman.Users.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
public class GityaAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double jointAccountBalance;
    private Long inviteCode;
    private String username;
    private int numberOfUsers;
    private double remainingBalance;

    private List<User> users = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    public void depositEqualAmount(double amount) {
        if (amount > 0) {
            this.remainingBalance += amount * this.numberOfUsers;
            return;
        }
    }
    public void addUser(String phoneNumber){
        for(User user : users){
            if(user.getPhoneNumber().equals(phoneNumber)){
                return;
            }
            numberOfUsers++;
        }
    }
    public void removeUser(String phoneNumber){

    }
}


