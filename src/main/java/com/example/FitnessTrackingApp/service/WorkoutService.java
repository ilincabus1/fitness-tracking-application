package com.example.FitnessTrackingApp.service;

import com.example.FitnessTrackingApp.model.dto.WorkoutDto;
import com.example.FitnessTrackingApp.model.entities.Workout;

import java.util.List;
import java.util.Optional;

public interface WorkoutService {

    public WorkoutDto createWorkout(WorkoutDto workoutDto);

    public Optional<Workout> findWorkoutById(Long id);

    public void deleteWorkout(Long id);

    public List<WorkoutDto> findAllWorkoutsDto();

    public Workout toEntity(WorkoutDto workoutDto);

    public WorkoutDto toDto(Workout workout);

    public WorkoutDto updateWorkout(Long id, WorkoutDto dto);
}
