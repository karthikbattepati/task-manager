package com.taskmanager.taskmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.repository.TaskRepository;
import com.taskmanager.taskmanager.repository.UserRepository;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private TaskRepository taskRepo;

    // 🔹 SIGNUP
    @PostMapping("/signup")
    public String signup(@RequestBody User user) {

        if (repo.findByUsername(user.getUsername()) != null) {
            return "USERNAME EXISTS";
        }

        repo.save(user);
        return "SUCCESS";
    }

    // 🔹 LOGIN
    @PostMapping("/login")
    public Long login(@RequestBody User user) {

        User u = repo.findByUsername(user.getUsername());

        if (u != null && u.getPassword().equals(user.getPassword())) {
            return u.getId();
        }

        throw new RuntimeException("Invalid login");
    }

    // 🔥 DELETE ACCOUNT (FULL FIX)
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, @RequestBody User request) {

        User user = repo.findById(id).orElseThrow();

        // 🔒 Password check
        if (!user.getPassword().equals(request.getPassword())) {
            return "WRONG PASSWORD";
        }

        // 🔥 DELETE ALL TASKS FIRST
        List<Task> tasks = taskRepo.findByUserId(id);
        taskRepo.deleteAll(tasks);

        // 🔥 DELETE USER
        repo.delete(user);

        return "ACCOUNT DELETED";
    }
}