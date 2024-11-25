package com.example.cornerstone_project_Aman.GtiyaAccount.controller;

import com.example.cornerstone_project_Aman.GtiyaAccount.bo.AddUserToGityaAccountRequest;
import com.example.cornerstone_project_Aman.GtiyaAccount.bo.GityaAccountRequest;
import com.example.cornerstone_project_Aman.GtiyaAccount.bo.GityaAccountResponse;
import com.example.cornerstone_project_Aman.GtiyaAccount.service.GityaAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class GityaAccountController {


    private final GityaAccountService accountService;

    public GityaAccountController(GityaAccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping("/createGityaAccount")
    public ResponseEntity<GityaAccountResponse> createAccountWithUsers(@RequestBody GityaAccountRequest request) {

        GityaAccountResponse response = accountService.createGityaAccount(request);
        System.out.println(response);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/addUserToAccount")
    public ResponseEntity<GityaAccountResponse> addUserToGityaAccount(@RequestBody AddUserToGityaAccountRequest addUserRequest) {

        GityaAccountResponse response = accountService.addUserToGityaAccount(addUserRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/join")
    public ResponseEntity<GityaAccountResponse> joinGityaAccount(@RequestBody AddUserToGityaAccountRequest addUserRequest) {
        GityaAccountResponse response = accountService.joinGityaAccount(addUserRequest);
        return ResponseEntity.ok(response);
    }
}

