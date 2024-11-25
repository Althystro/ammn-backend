package com.example.cornerstone_project_Aman.GtiyaAccount.service;

import com.example.cornerstone_project_Aman.GtiyaAccount.bo.AddUserToGityaAccountRequest;
import com.example.cornerstone_project_Aman.GtiyaAccount.bo.GityaAccountRequest;
import com.example.cornerstone_project_Aman.GtiyaAccount.bo.GityaAccountResponse;
import com.example.cornerstone_project_Aman.GtiyaAccount.entity.GityaAccount;
import com.example.cornerstone_project_Aman.GtiyaAccount.repository.GityaAccountRepository;
import com.example.cornerstone_project_Aman.Users.entity.User;
import com.example.cornerstone_project_Aman.Users.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;


@Service
public class GityaAccountService {

    private final GityaAccountRepository accountRepository;

    private final UserRepository userRepository;


    public GityaAccountService(GityaAccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;

    }


    public GityaAccountResponse createGityaAccount(GityaAccountRequest accountRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        GityaAccount account = new GityaAccount();

        account.setJointAccountBalance(accountRequest.getJointAccountBalance());

        Random random = new Random();
        Long randomNumber = 1000000 + random.nextLong(9000000);
        account.setInviteCode(randomNumber);
        account.setAccountName(accountRequest.getAccountName());
        account.setNumberOfUsers(0);
        account.setRemainingBalance(accountRequest.getJointAccountBalance() != null
                ? accountRequest.getJointAccountBalance() : 0.0);
        account.setUsers(new ArrayList<>());

        currentUser.getGityaAccountList().add(account);
        account.getUsers().add(currentUser);

        currentUser = userRepository.save(currentUser);
        account = accountRepository.save(account);


        return new GityaAccountResponse(account);
    }


    @Transactional
    public GityaAccountResponse addUserToGityaAccount(AddUserToGityaAccountRequest addUserRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        GityaAccount account = accountRepository.findByAccountName(addUserRequest.getAccountName())
                .orElseThrow(() -> new RuntimeException("Gitiya Account not found with name: " + addUserRequest.getAccountName()));

        User userToAdd = userRepository.findByEmail(addUserRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + addUserRequest.getEmail()));

        if (account.getUsers().contains(userToAdd)) {
            throw new RuntimeException("User is already part of this Gitiya Account.");
        }


        account.addUser(userToAdd);

        userRepository.save(userToAdd);

        return new GityaAccountResponse(account);
    }

    @Transactional
    public GityaAccountResponse joinGityaAccount(AddUserToGityaAccountRequest addUserRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        GityaAccount account = accountRepository.findByInviteCode(addUserRequest.getInviteCode())
                .orElseThrow(() -> new RuntimeException("Gitiya Account not found with invite code: " + addUserRequest.getInviteCode()));

        if (account.getUsers().contains(currentUser)) {
            throw new RuntimeException("You are already a member of this Gitiya Account.");
        }

        account.getUsers().add(currentUser);

        currentUser.getGityaAccountList().add(account);

        account.setNumberOfUsers(account.getUsers().size());

        userRepository.save(currentUser);
        account = accountRepository.save(account);

        return new GityaAccountResponse(account);
    }

//    public List<GityaAccount> getAuthenticatedUserGityaAccounts() {
//        // Get the authenticated user from the security context
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName(); // Assumes email is used as username
//
//        // Fetch the user by email
//        User authenticatedUser = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
//
//        // Return the user's Gitya Account list
//        return authenticatedUser.getGityaAccountList();
//    }
}





