package com.example.cornerstone_project_Aman.Auth.repositories;

import com.example.cornerstone_project_Aman.Auth.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}