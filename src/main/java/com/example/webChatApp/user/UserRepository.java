package com.example.webChatApp.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
    public Optional<User> findByEmail(String email);
    public Optional<User> findByUID(int UID);
}