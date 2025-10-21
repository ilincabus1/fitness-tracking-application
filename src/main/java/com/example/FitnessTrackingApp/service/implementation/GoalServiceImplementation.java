package com.example.FitnessTrackingApp.service.implementation;

import com.example.FitnessTrackingApp.model.dto.GoalDto;
import com.example.FitnessTrackingApp.model.entities.Goal;
import com.example.FitnessTrackingApp.model.entities.User;
import com.example.FitnessTrackingApp.model.entities.Workout;
import com.example.FitnessTrackingApp.model.enums.GoalStatus;
import com.example.FitnessTrackingApp.repo.GoalRepository;
import com.example.FitnessTrackingApp.repo.UserRepository;
import com.example.FitnessTrackingApp.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GoalServiceImplementation implements GoalService {
    @Autowired
    GoalRepository goalRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public GoalDto createGoal(GoalDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Goal goal = new Goal();
        goal.setUserId(user.getId());
        goal.setDailySteps(dto.getDailySteps());
        goal.setNoKm(dto.getNoKm());
        goal.setTotalWeightTargeted(dto.getTotalWeightTargeted());
        goal.setStatus(dto.getStatus());
        goalRepository.save(goal);
        return toDto(goal);
    }

    @Override
    public List<GoalDto> findAll() {
        List<Goal> goals = goalRepository.findAll();
        return goals.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GoalDto> findGoalsByUserId(Long userId) {
        return goalRepository.findByUserId(userId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void cancelGoal(Long id) {
        Goal goalToDelete = goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found with id: " + id));
        Set<Workout> workoutsToUpdate = new HashSet<>(goalToDelete.getContributingWorkouts());
        for (Workout workout : workoutsToUpdate) {
            workout.getContributingGoals().remove(goalToDelete);
        }
        goalRepository.delete(goalToDelete);
    }

    @Override
    public GoalDto updateGoalStatus(Long goalId, GoalStatus newStatus) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal not found with ID: " + goalId));
        goal.setStatus(newStatus);
        Goal updatedGoal = goalRepository.save(goal);
        return toDto(updatedGoal);
    }

    public GoalDto toDto(Goal goal) {
        GoalDto dto = new GoalDto();
        if (goal.getUserId() != null) dto.setUserId(goal.getUserId());
        dto.setTotalWeightTargeted(goal.getTotalWeightTargeted());
        dto.setNoKm(goal.getNoKm());
        dto.setDailySteps(goal.getDailySteps());
        dto.setStatus(goal.getStatus());
        return dto;
    }
}
