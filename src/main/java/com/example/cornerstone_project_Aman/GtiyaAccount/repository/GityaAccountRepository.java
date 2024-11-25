package com.example.cornerstone_project_Aman.GtiyaAccount.repository;

import com.example.cornerstone_project_Aman.GtiyaAccount.entity.GityaAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GityaAccountRepository extends JpaRepository<GityaAccount, Long> {
    Optional<GityaAccount> findByAccountName(String accountName);

    Optional<GityaAccount> findByInviteCode(Long inviteCode);
}


