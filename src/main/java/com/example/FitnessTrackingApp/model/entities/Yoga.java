package com.example.FitnessTrackingApp.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("YOGA")
public class Yoga extends Workout {
    private String style;
    private Integer flexibilityGrade;
    private Integer breath;
    private Integer stressLevels;
}
