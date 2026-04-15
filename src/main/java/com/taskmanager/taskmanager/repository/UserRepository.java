package com.taskmanager.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanager.taskmanager.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}