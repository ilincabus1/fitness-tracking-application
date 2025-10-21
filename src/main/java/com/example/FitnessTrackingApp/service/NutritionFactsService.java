package com.example.FitnessTrackingApp.service;

import com.example.FitnessTrackingApp.model.dto.NutritionFactsDto;
import com.example.FitnessTrackingApp.model.entities.NutritionFacts;

import java.util.List;
import java.util.Optional;

public interface NutritionFactsService {
    List<NutritionFacts> findAll();

    Optional<NutritionFacts> findById(Long id);

    NutritionFacts findByFoodName(String foodName);

    NutritionFacts update(NutritionFacts nutritionFacts);

    void deleteById(Long id);

    NutritionFactsDto toDto(NutritionFacts entity);

    NutritionFacts toEntity(NutritionFactsDto dto);

    NutritionFactsDto saveFromDto(NutritionFactsDto dto);

    NutritionFactsDto updateFromDto(Long id, NutritionFactsDto dto);
}
