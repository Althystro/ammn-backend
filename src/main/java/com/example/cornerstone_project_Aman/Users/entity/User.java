package com.example.cornerstone_project_Aman.Users.entity;

import com.example.cornerstone_project_Aman.GtiyaAccount.entity.GityaAccount;
import com.example.cornerstone_project_Aman.Wallet.entity.Wallet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true, length = 100, nullable = false)
    private String email;
    @Column(nullable = true)
    private String password;
    @Column(nullable = true)
    private String civilId;
    @Column(nullable = true)
    private String phoneNumber;
    @CreationTimestamp
    @Column(updatable = true, name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "wallet_id", referencedColumnName = "id", nullable = true)
    @JsonIgnoreProperties(value = {"user"})
    private Wallet wallet;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_gitya_account", // Name of the join table
            joinColumns = @JoinColumn(name = "user_id"), // Foreign key for User
            inverseJoinColumns = @JoinColumn(name = "gitya_account_id") // Foreign key for GityaAccount
    )
//    @JsonIgnoreProperties(value = {"users"})
    private List<GityaAccount> gityaAccountList = new ArrayList<>();


    public List<GityaAccount> getGityaAccountList() {
        return gityaAccountList;
    }

    public void setGityaAccountList(List<GityaAccount> gityaAccountList) {
        this.gityaAccountList = gityaAccountList;
    }

    public List<GityaAccount> addToGityaAccountList(GityaAccount gityaAccount) {
        gityaAccountList.add(gityaAccount);
        return this.gityaAccountList;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCivilId() {
        return civilId;
    }

    public void setCivilId(String civilId) {
        this.civilId = civilId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
        if (wallet != null && wallet.getUser() != this) { // Ensure bidirectional consistency
            wallet.setUser(this);
        }
    }

    public void addGityaAccount(GityaAccount account) {
        if (!this.gityaAccountList.contains(account)) {
            this.gityaAccountList.add(account);
            account.getUsers().add(this); // Synchronize both sides
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // Modify to return actual roles/authorities if needed.
    }

    @Override
    public String getUsername() {
        return email; // Typically, username is the email.
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Modify based on your business logic.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Modify based on your business logic.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Modify based on your business logic.
    }

    @Override
    public boolean isEnabled() {
        return true; // Modify based on your business logic.
    }
}
