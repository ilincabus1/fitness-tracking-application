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
@DiscriminatorValue("WEIGHTLIFTING")
public class Weightlifting extends Workout {
    private Double weightLifted;
    private Integer sets;
    private Integer reps;
}
