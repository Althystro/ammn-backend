package com.example.cornerstone_project_Aman.Users.repository;

import com.example.cornerstone_project_Aman.Users.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    User findByPhoneNumber(String email);

}