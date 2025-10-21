package com.example.FitnessTrackingApp.repo;

import com.example.FitnessTrackingApp.model.entities.NutritionFacts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionFactsRepository extends JpaRepository<NutritionFacts, Long> {
    NutritionFacts findByFoodName(String foodName);
}
