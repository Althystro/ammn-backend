package com.example.cornerstone_project_Aman.Auth.config;

import com.example.cornerstone_project_Aman.Auth.bo.LoginUserRequest;
import com.example.cornerstone_project_Aman.Auth.bo.RegisterUserRequest;
import com.example.cornerstone_project_Aman.Transactions.entity.Transactions;
import com.example.cornerstone_project_Aman.Users.entity.User;
import com.example.cornerstone_project_Aman.Users.repository.UserRepository;
import com.example.cornerstone_project_Aman.Wallet.entity.Wallet;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserRequest input) {
        User user = new User();
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setCivilId(input.getCivilId());
        user.setPhoneNumber(input.getPhoneNumber());
        user.setEmail(input.getEmail());
        Wallet wallet = new Wallet();
        List<Transactions> transactions = new ArrayList<>();
//        List<GityaAccount> gityaAccountList = new ArrayList<>();
        wallet.setTransactions(transactions);
        user.setWallet(wallet);
//        user.setGityaAccountList(gityaAccountList);
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(LoginUserRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}