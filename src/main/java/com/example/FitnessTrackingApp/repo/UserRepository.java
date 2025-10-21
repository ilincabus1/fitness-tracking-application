package com.example.FitnessTrackingApp.repo;

import com.example.FitnessTrackingApp.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
