package com.example.FitnessTrackingApp.model.dto;

import com.example.FitnessTrackingApp.model.enums.GoalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoalDto {
    private double noKm;
    private Integer dailySteps;
    private double totalWeightTargeted;
    private long userId;
    private GoalStatus status;
}
