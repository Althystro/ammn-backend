package com.example.cornerstone_project_Aman.Wallet.entity;

import com.example.cornerstone_project_Aman.Transactions.entity.Transactions;
import com.example.cornerstone_project_Aman.Users.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    //    @Column(nullable = false)
    private double balance;

    // One-to-One relationship with User
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"wallet"})
    private User user;

    // One-to-Many relationship with Transactions
    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"wallet"})

    private List<Transactions> transactions;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user; // Ensure bidirectional consistency if needed.
        if (user != null && user.getWallet() != this) {
            user.setWallet(this);
        }
    }

    public List<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transactions> transactions) {
        this.transactions = transactions;
        for (Transactions transaction : transactions) {
            transaction.setWallet(this); // Ensure bidirectional consistency
        }
    }
}
