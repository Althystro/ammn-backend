package com.example.cornerstone_project_Aman.Users.service;

import com.example.cornerstone_project_Aman.Users.bo.ProfileResponse;
import com.example.cornerstone_project_Aman.Users.entity.User;
import com.example.cornerstone_project_Aman.Users.repository.UserRepository;
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

    public ProfileResponse myUsername() {
        ProfileResponse myUser = new ProfileResponse();

        return myUser;
    }

}