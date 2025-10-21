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
@DiscriminatorValue("SWIMMING")
public class Swimming extends Workout {
    private Double swimmingPace;
    private String strokeType;
}
