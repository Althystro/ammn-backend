package com.example.cornerstone_project_Aman.GtiyaAccount.entity;

import com.example.cornerstone_project_Aman.Users.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class GityaAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double jointAccountBalance;

    @Column(nullable = false)
    private Long inviteCode;

    @Column(nullable = false)
    private String accountName;


    @Column(nullable = false)
    private int numberOfUsers;

    @Column(nullable = false)
    private double remainingBalance;


    @ManyToMany(mappedBy = "gityaAccountList", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"gityaAccountList"})
    private List<User> users;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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

    public void addToUsers(User user) {
        this.users.add(user);
    }

    public void depositEqualAmount(double amount) {
        if (amount > 0) {
            this.remainingBalance += amount * this.numberOfUsers;
        }
    }

    public void addUser(User user) {
        if (!this.users.contains(user)) {
            this.users.add(user);
            user.getGityaAccountList().add(this);
        }
    }


    public void removeUser(String phoneNumber) {

    }
}


