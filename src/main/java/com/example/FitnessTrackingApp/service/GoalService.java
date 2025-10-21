package com.example.FitnessTrackingApp.service;

import com.example.FitnessTrackingApp.model.dto.GoalDto;
import com.example.FitnessTrackingApp.model.enums.GoalStatus;

import java.util.List;

public interface GoalService {
    GoalDto createGoal(GoalDto dto);

    List<GoalDto> findAll();

    List<GoalDto> findGoalsByUserId(Long userId);

    void cancelGoal(Long goalId);

    GoalDto updateGoalStatus(Long goalId, GoalStatus newStatus);
}
