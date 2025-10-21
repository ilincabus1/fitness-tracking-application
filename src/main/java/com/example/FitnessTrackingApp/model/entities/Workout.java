package com.example.FitnessTrackingApp.model.entities;

import com.example.FitnessTrackingApp.model.enums.WorkoutType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "workouts")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "workoutType", discriminatorType = DiscriminatorType.STRING)
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    private Long userId;
    protected String startTime;
    protected String endTime;
    protected Double caloriesBurned;
    protected Double distance;
    protected LocalDate workoutDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_night_sleep_id")
    private Sleep sleep;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "workout_nutrition_link",
            joinColumns = @JoinColumn(name = "workout_id"),
            inverseJoinColumns = @JoinColumn(name = "nutrition_id")
    )
    private Set<NutritionFacts> linkedNutrition;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "workout_goal_contributes",
            joinColumns = @JoinColumn(name = "workout_id"),
            inverseJoinColumns = @JoinColumn(name = "goal_id")
    )
    private Set<Goal> contributingGoals;

    public WorkoutType getWorkoutType() {
        if (this instanceof Running) return WorkoutType.RUNNING;
        if (this instanceof Weightlifting) return WorkoutType.WEIGHTLIFTING;
        if (this instanceof Swimming) return WorkoutType.SWIMMING;
        if (this instanceof Yoga) return WorkoutType.YOGA;
        throw new IllegalStateException("Unknown workout subclass: " + this.getClass());
    }
}
