package com.example.FitnessTrackingApp.repo;

import com.example.FitnessTrackingApp.model.entities.HeartRate;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRateRepository extends JpaRepository<HeartRate, Long> {
    List<HeartRate> findByWorkoutId(Long workoutId);

    @Transactional
    void deleteByWorkoutId(Long workoutId);
}
