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


    public ProfileResponse findByEmail(ProfileResponse email) {
        // Query the database for the user by email

        User user = userRepository.findByEmail(email.getEmail()).orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        // Map the User entity to a ProfileResponse DTO
        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setId(user.getId());
        profileResponse.setFirstName(user.getFirstName());
        profileResponse.setLastName(user.getLastName());
        profileResponse.setCivilId(user.getCivilId());
        profileResponse.setEmail(user.getEmail());
        profileResponse.setPhoneNumber(user.getPhoneNumber());
        // Add other fields as needed

        return profileResponse;
    }

    public User findByEmailString(String email) {
        // Query the database for the user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        // Map the User entity to a ProfileResponse DTO
        User profileResponse = new User();
        profileResponse.setId(user.getId());
        profileResponse.setFirstName(user.getFirstName());
        profileResponse.setLastName(user.getLastName());
        profileResponse.setCivilId(user.getCivilId());
        profileResponse.setEmail(user.getEmail());
        profileResponse.setPhoneNumber(user.getPhoneNumber());
        profileResponse.setWallet(user.getWallet());
        // Add other fields as needed

        return profileResponse;
    }
}


