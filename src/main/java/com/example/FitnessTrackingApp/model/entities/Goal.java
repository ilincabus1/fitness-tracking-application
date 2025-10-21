package com.example.FitnessTrackingApp.model.entities;

import com.example.FitnessTrackingApp.model.enums.GoalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private double noKm;
    private Integer dailySteps;
    private double totalWeightTargeted;

    @ManyToMany(mappedBy = "contributingGoals")
    private Set<Workout> contributingWorkouts = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private GoalStatus status = GoalStatus.ACTIVE;
}
