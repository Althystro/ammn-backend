package com.example.cornerstone_project_Aman.GtiyaAccount.controller;

import com.example.cornerstone_project_Aman.GtiyaAccount.bo.GityaAccountRequest;
import com.example.cornerstone_project_Aman.GtiyaAccount.bo.GityaAccountResponse;
import com.example.cornerstone_project_Aman.GtiyaAccount.service.GityaAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class GityaAccountController {


    private GityaAccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity<GityaAccountResponse> getAccountById(@PathVariable Long id) {
        GityaAccountResponse response = accountService.getAccountById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GityaAccountResponse> createAccount(@RequestBody GityaAccountRequest request) {
        GityaAccountResponse response = accountService.createAccount(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/deposit")
    public ResponseEntity<GityaAccountResponse> depositEqualAmount(
            @PathVariable Long id,
            @RequestParam double amountPerUser) {
        GityaAccountResponse accountResponse = accountService.depositEqualAmount(id, amountPerUser);
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }
    public ResponseEntity<GityaAccountResponse> addUser(
            @PathVariable Long id,
            @RequestParam String phoneNumber,
            @RequestParam String adminPhoneNumber) {
        GityaAccountResponse response = accountService.addUser(id, phoneNumber, adminPhoneNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
//    @PostMapping("/{accountId}/add-user")
//    public ResponseEntity<GityaAccountResponse> addUser(
//            @PathVariable Long accountId,
//            @RequestParam String phoneNumber,
//            @RequestParam String userPhoneNumber) {
//
//        GityaAccountResponse accountResponse = GityaAccountService.addUser(accountId, phoneNumber, userPhoneNumber);
//        return ResponseEntity.ok(accountResponse);
//    }


}

