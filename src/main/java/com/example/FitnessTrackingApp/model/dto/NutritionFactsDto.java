package com.example.FitnessTrackingApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutritionFactsDto {

    private String foodName;
    private int servingSize;
    private double calories;
    private double totalFat;
    private double totalCarbohydrates;
    private double dietaryFiber;
    private double totalSugars;
    private double protein;
    private Long userId;
}