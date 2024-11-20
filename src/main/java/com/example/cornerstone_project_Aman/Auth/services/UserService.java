package com.example.cornerstone_project_Aman.Auth.services;

import com.example.cornerstone_project_Aman.Auth.entity.User;
import com.example.cornerstone_project_Aman.Auth.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}