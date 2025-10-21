package com.example.FitnessTrackingApp.service.implementation;

import com.example.FitnessTrackingApp.model.dto.NutritionFactsDto;
import com.example.FitnessTrackingApp.model.entities.NutritionFacts;
import com.example.FitnessTrackingApp.model.entities.Workout;
import com.example.FitnessTrackingApp.repo.NutritionFactsRepository;
import com.example.FitnessTrackingApp.service.NutritionFactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NutritionFactsServiceImplementation implements NutritionFactsService {
    @Autowired
    NutritionFactsRepository nutritionFactsRepository;

    @Override
    public List<NutritionFacts> findAll() {
        return nutritionFactsRepository.findAll();
    }

    @Override
    public Optional<NutritionFacts> findById(Long id) {
        return nutritionFactsRepository.findById(id);
    }

    @Override
    public NutritionFacts findByFoodName(String foodName) {
        return nutritionFactsRepository.findByFoodName(foodName);
    }

    @Override
    public NutritionFacts update(NutritionFacts nutritionFacts) {
        return nutritionFactsRepository.save(nutritionFacts);
    }

    @Override
    public void deleteById(Long id) {
        NutritionFacts entityToDelete = nutritionFactsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nutrition fact not found"));
        Set<Workout> workoutsToUpdate = new HashSet<>(entityToDelete.getLinkedWorkouts());

        for (Workout workout : workoutsToUpdate) {
            workout.getLinkedNutrition().remove(entityToDelete);
        }
        nutritionFactsRepository.delete(entityToDelete);
    }

    public NutritionFactsDto toDto(NutritionFacts entity) {
        if (entity == null) {
            return null;
        }
        NutritionFactsDto dto = new NutritionFactsDto();
        dto.setFoodName(entity.getFoodName());
        dto.setUserId(entity.getUserId());
        dto.setServingSize(entity.getServingSize());
        dto.setCalories(entity.getCalories());
        dto.setTotalFat(entity.getTotalFat());
        dto.setTotalCarbohydrates(entity.getTotalCarbohydrates());
        dto.setDietaryFiber(entity.getDietaryFiber());
        dto.setTotalSugars(entity.getTotalSugars());
        dto.setProtein(entity.getProtein());
        return dto;
    }

    public NutritionFacts toEntity(NutritionFactsDto dto) {
        if (dto == null) {
            return null;
        }
        NutritionFacts entity = new NutritionFacts();
        entity.setFoodName(dto.getFoodName());
        entity.setUserId(dto.getUserId());
        entity.setServingSize(dto.getServingSize());
        entity.setCalories(dto.getCalories());
        entity.setTotalFat(dto.getTotalFat());
        entity.setTotalCarbohydrates(dto.getTotalCarbohydrates());
        entity.setDietaryFiber(dto.getDietaryFiber());
        entity.setTotalSugars(dto.getTotalSugars());
        entity.setProtein(dto.getProtein());
        return entity;
    }

    @Override
    public NutritionFactsDto saveFromDto(NutritionFactsDto dto) {
        NutritionFacts entity = toEntity(dto);
        NutritionFacts savedEntity = nutritionFactsRepository.save(entity);
        return toDto(savedEntity);
    }

    public NutritionFactsDto updateFromDto(Long id, NutritionFactsDto dto) {
        NutritionFacts existing = nutritionFactsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nutrition fact not found with id: " + id));
        existing.setFoodName(dto.getFoodName());
        existing.setUserId(dto.getUserId());
        existing.setServingSize(dto.getServingSize());
        existing.setCalories(dto.getCalories());
        existing.setTotalFat(dto.getTotalFat());
        existing.setTotalCarbohydrates(dto.getTotalCarbohydrates());
        existing.setDietaryFiber(dto.getDietaryFiber());
        existing.setTotalSugars(dto.getTotalSugars());
        existing.setProtein(dto.getProtein());
        NutritionFacts saved = nutritionFactsRepository.save(existing);
        return toDto(saved);
    }
}
