package com.example.cornerstone_project_Aman.GtiyaAccount.service;

import com.example.cornerstone_project_Aman.GtiyaAccount.bo.GityaAccountRequest;
import com.example.cornerstone_project_Aman.GtiyaAccount.bo.GityaAccountResponse;
import com.example.cornerstone_project_Aman.GtiyaAccount.entity.GityaAccount;
import com.example.cornerstone_project_Aman.GtiyaAccount.repository.GityaAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GityaAccountService {

    private final GityaAccountRepository accountRepository;


    public GityaAccountService(GityaAccountRepository accountRepository) {
        this.accountRepository = accountRepository;

    }

    // Get account by ID
    public GityaAccountResponse getAccountById(Long id) {
        GityaAccount account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + id));
        return new GityaAccountResponse(account);
    }

    // Create new account
    public GityaAccountResponse createAccount(GityaAccountRequest accountRequest) {
        GityaAccount account = new GityaAccount();
        account.setJointAccountBalance(accountRequest.getJointAccountBalance());
        account.setInviteCode(accountRequest.getInviteCode());
        account.setUsername(accountRequest.getUsername());
        account.setNumberOfUsers(accountRequest.getNumberOfUsers());
        account.setRemainingBalance(accountRequest.getJointAccountBalance() != null
                ? accountRequest.getJointAccountBalance() : 0.0);

        account = accountRepository.save(account);
        return new GityaAccountResponse(account);
    }

    // Deposit equal amount into the account
    public GityaAccountResponse depositEqualAmount(Long accountId, double amountPerUser) {
        GityaAccount account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + accountId));

        account.depositEqualAmount(amountPerUser);
        account = accountRepository.save(account);

        return new GityaAccountResponse(account);
    }

    public GityaAccountResponse addUser(Long accountId, String phoneNumber, String userPhoneNumber) {
        GityaAccount account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + accountId));
        account.addUser(phoneNumber);
        account = accountRepository.save(account);

        return new GityaAccountResponse(account);
    }
}





