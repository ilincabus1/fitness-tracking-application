package com.example.FitnessTrackingApp.controller;

import com.example.FitnessTrackingApp.model.dto.NutritionFactsDto;
import com.example.FitnessTrackingApp.service.NutritionFactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/nutritionfacts")
public class NutritionFactsController {
    @Autowired
    NutritionFactsService nutritionFactsService;

    @GetMapping
    public ResponseEntity<List<NutritionFactsDto>> findAll() {
        List<NutritionFactsDto> dtos = nutritionFactsService.findAll().stream()
                .map(nutritionFactsService::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NutritionFactsDto> findById(@PathVariable Long id) {
        return nutritionFactsService.findById(id)
                .map(nutritionFactsService::toDto) // Convert found Entity to DTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/food/{foodName}")
    public ResponseEntity<NutritionFactsDto> findByFoodName(@PathVariable String foodName) {
        // Assuming findByFoodName returns an Entity
        return Optional.ofNullable(nutritionFactsService.findByFoodName(foodName))
                .map(nutritionFactsService::toDto) // Convert to DTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NutritionFactsDto> save(@RequestBody NutritionFactsDto nutritionFactsDTO) {
        NutritionFactsDto savedDto = nutritionFactsService.saveFromDto(nutritionFactsDTO);
        return ResponseEntity.ok(savedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        nutritionFactsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<NutritionFactsDto> update(@PathVariable Long id, @RequestBody NutritionFactsDto nutritionFactsDto) {
        NutritionFactsDto updatedDto = nutritionFactsService.updateFromDto(id, nutritionFactsDto);
        return ResponseEntity.ok(updatedDto);
    }
}
