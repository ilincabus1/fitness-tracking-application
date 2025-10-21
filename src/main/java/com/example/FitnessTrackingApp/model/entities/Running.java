package com.example.FitnessTrackingApp.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("RUNNING")
public class Running extends Workout {
    private Integer cadence;
    private Double runningPace;
    private Double elevation;
    private String mileSplits;
}
