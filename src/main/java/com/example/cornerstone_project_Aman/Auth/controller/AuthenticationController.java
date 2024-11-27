package com.example.cornerstone_project_Aman.Auth.controller;

import com.example.cornerstone_project_Aman.Auth.bo.LoginResponse;
import com.example.cornerstone_project_Aman.Auth.bo.LoginUserRequest;
import com.example.cornerstone_project_Aman.Auth.bo.RegisterResponse;
import com.example.cornerstone_project_Aman.Auth.bo.RegisterUserRequest;
import com.example.cornerstone_project_Aman.Auth.config.AuthenticationService;
import com.example.cornerstone_project_Aman.Auth.service.JwtService;
import com.example.cornerstone_project_Aman.Users.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterUserRequest registerUserRequest) {
        User registeredUser = authenticationService.signup(registerUserRequest);
        String jwtToken = jwtService.generateToken(registeredUser);

        RegisterResponse registerResponse = new RegisterResponse();
//        registerResponse.setId(registerResponse.getId());
//        registerResponse.setFirstName(registeredUser.getFirstName());
//        registerResponse.setLastName(registeredUser.getLastName());
//        registerResponse.setEmail(registeredUser.getEmail());
//        registerResponse.setPhoneNumber(registeredUser.getPhoneNumber());
//        registerResponse.setCivilId(registeredUser.getCivilId());
        registerResponse.setToken(jwtToken);
//        long minutes = TimeUnit.MILLISECONDS.toMinutes(jwtService.getExpirationTime());
//        registerResponse.setExpiresIn(minutes);
        return ResponseEntity.ok(registerResponse);


    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserRequest loginUserRequest) {
        User authenticatedUser = authenticationService.authenticate(loginUserRequest);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
//        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
