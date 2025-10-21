package com.example.FitnessTrackingApp.model.dto;

import com.example.FitnessTrackingApp.model.enums.WorkoutType;
import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public abstract class WorkoutDto {
    private String startTime;
    private String endTime;
    private Double caloriesBurned;
    private Double distance;
    private LocalDate workoutDate;

    private Long sleepId;
    private Long userId;
    private Set<Long> linkedNutritionIds;
    private Set<Long> contributingGoalIds;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private WorkoutType workoutType;

    protected WorkoutDto(String startTime, String endTime, Double caloriesBurned, Double distance, LocalDate workoutDate, Long sleepId, Long userId, Set<Long> linkedNutritionIds, Set<Long> contributingGoalIds, WorkoutType workoutType) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.caloriesBurned = caloriesBurned;
        this.distance = distance;
        this.workoutDate = workoutDate;
        this.sleepId = sleepId;
        this.userId = userId;
        this.linkedNutritionIds = linkedNutritionIds;
        this.contributingGoalIds = contributingGoalIds;
        this.workoutType = workoutType;
    }

    public WorkoutType getWorkoutType() {
        if (workoutType != null) return workoutType;
        if (this instanceof YogaDto) {
            return WorkoutType.YOGA;
        } else if (this instanceof WeightliftingDto) {
            return WorkoutType.WEIGHTLIFTING;
        } else if (this instanceof RunningDto) {
            return WorkoutType.RUNNING;
        } else if (this instanceof SwimmingDto) {
            return WorkoutType.SWIMMING;
        }
        return null;
    }
}
