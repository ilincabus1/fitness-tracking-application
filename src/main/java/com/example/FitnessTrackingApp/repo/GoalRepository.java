package com.example.FitnessTrackingApp.repo;

import com.example.FitnessTrackingApp.model.entities.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByUserId(Long userId);
}
