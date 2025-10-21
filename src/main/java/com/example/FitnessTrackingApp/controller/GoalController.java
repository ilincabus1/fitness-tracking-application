package com.example.FitnessTrackingApp.controller;

import com.example.FitnessTrackingApp.model.dto.GoalDto;
import com.example.FitnessTrackingApp.model.entities.Goal;
import com.example.FitnessTrackingApp.model.enums.GoalStatus;
import com.example.FitnessTrackingApp.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalController {
    @Autowired
    GoalService goalService;

    @PostMapping
    public ResponseEntity<GoalDto> createGoal(@RequestBody GoalDto dto) {
        return ResponseEntity.ok(goalService.createGoal(dto));
    }

    @DeleteMapping("/cancel/{goalId}")
    public ResponseEntity<Void> cancel(@PathVariable Long goalId) {
        goalService.cancelGoal(goalId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<GoalDto>> findGoalsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(goalService.findGoalsByUserId(userId));
    }

    @GetMapping
    public ResponseEntity<List<GoalDto>> getAllGoals() {
        return ResponseEntity.ok(goalService.findAll());
    }

    @PutMapping("/{goalId}/status")
    public ResponseEntity<GoalDto> updateGoalStatus(@PathVariable Long goalId, @RequestParam GoalStatus status) {
        GoalDto updatedGoal = goalService.updateGoalStatus(goalId, status);
        return ResponseEntity.ok(updatedGoal);
    }
}
