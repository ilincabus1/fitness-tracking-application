package com.example.FitnessTrackingApp.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "nutritionFacts")
public class NutritionFacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String foodName;
    private int servingSize;
    private double calories;
    private double totalFat;
    private double totalCarbohydrates;
    private double dietaryFiber;
    private double totalSugars;
    private double protein;

    @ManyToMany(mappedBy = "linkedNutrition")
    private Set<Workout> linkedWorkouts;
}
