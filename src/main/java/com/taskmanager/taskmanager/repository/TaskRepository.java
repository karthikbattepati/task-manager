package com.taskmanager.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanager.taskmanager.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // 🔥 REQUIRED FOR DELETE FIX
    List<Task> findByUserId(Long userId);
}