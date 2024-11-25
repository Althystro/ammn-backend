package com.example.cornerstone_project_Aman.Users.controller;

import com.example.cornerstone_project_Aman.Users.entity.User;
import com.example.cornerstone_project_Aman.Users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }


//    @PostMapping("/findUserByEmail")
//    public ResponseEntity<ProfileResponse> allUsers(@RequestBody ProfileResponse email) {
//        ProfileResponse user = userService.findByEmail(email);
//        return ResponseEntity.ok(user);
//    }
}