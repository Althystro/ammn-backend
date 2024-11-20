package com.example.cornerstone_project_Aman.Auth.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Table(name = "users")
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    // Getters and setters for fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    // Implementations of UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the roles or authorities granted to the user. For now, you can return null or an empty list.
        return null;  // You should modify this to return actual roles.
    }

    @Override
    public String getUsername() {
        // The username is typically the email in many applications.
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Return true if the account is not expired.
        return true;  // Modify based on your business logic.
    }

    @Override
    public boolean isAccountNonLocked() {
        // Return true if the account is not locked.
        return true;  // Modify based on your business logic.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Return true if the credentials (password) are not expired.
        return true;  // Modify based on your business logic.
    }

    @Override
    public boolean isEnabled() {
        // Return true if the user is enabled (active).
        return true;  // Modify based on your business logic.
    }
}
