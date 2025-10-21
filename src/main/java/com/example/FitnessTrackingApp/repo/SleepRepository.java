package com.example.FitnessTrackingApp.repo;

import com.example.FitnessTrackingApp.model.entities.Sleep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SleepRepository extends JpaRepository<Sleep, Long> {
    List<Sleep> findByUserId(Long userId);
}
