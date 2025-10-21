package com.example.FitnessTrackingApp.service.implementation;

import com.example.FitnessTrackingApp.model.dto.*;
import com.example.FitnessTrackingApp.model.entities.*;
import com.example.FitnessTrackingApp.model.enums.WorkoutType;
import com.example.FitnessTrackingApp.repo.*;
import com.example.FitnessTrackingApp.service.WorkoutService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WorkoutServiceImplementation implements WorkoutService {
    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    SleepRepository sleepRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NutritionFactsRepository nutritionFactsRepository;

    @Autowired
    GoalRepository goalRepository;

    @Override
    public WorkoutDto createWorkout(WorkoutDto workoutDto) {
        Workout workoutEntity = toEntity(workoutDto);
        Workout savedWorkout = workoutRepository.save(workoutEntity);
        return toDto(savedWorkout);
    }

    @Override
    public List<WorkoutDto> findAllWorkoutsDto() { //
        return workoutRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Workout> findWorkoutById(Long id) {
        return workoutRepository.findById(id);
    }

    @Override
    public WorkoutDto updateWorkout(Long id, WorkoutDto workoutDto) {
        Workout workoutFound = workoutRepository.findById(id).orElseThrow(() -> new RuntimeException("Workout not found"));
        workoutFound.setStartTime(workoutDto.getStartTime());
        workoutFound.setEndTime(workoutDto.getEndTime());
        workoutFound.setCaloriesBurned(workoutDto.getCaloriesBurned());
        workoutFound.setDistance(workoutDto.getDistance());
        workoutFound.setWorkoutDate(workoutDto.getWorkoutDate());

        User user = userRepository.findById(workoutDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + workoutDto.getUserId()));
        workoutFound.setUserId(user.getId());

        Sleep sleep = sleepRepository.findById(workoutDto.getSleepId())
                .orElseThrow(() -> new EntityNotFoundException("Sleep record not found with ID: " + workoutDto.getSleepId()));
        workoutFound.setSleep(sleep);

        List<Goal> goalList = goalRepository.findAllById(workoutDto.getContributingGoalIds());
        workoutFound.setContributingGoals(new HashSet<>(goalList));

        Set<Long> nutritionFactIds = workoutDto.getLinkedNutritionIds();
        List<NutritionFacts> factList = nutritionFactsRepository.findAllById(nutritionFactIds);
        if (factList.size() != nutritionFactIds.size()) {
            throw new EntityNotFoundException("One or more linked NutritionFact entities were not found.");
        }
        Set<NutritionFacts> nutritionFactSet = new HashSet<>(factList);
        workoutFound.setLinkedNutrition(nutritionFactSet);

        WorkoutType workoutType = workoutDto.getWorkoutType();
        if (workoutType != null && workoutType != workoutFound.getWorkoutType()) {
            throw new IllegalArgumentException("Cannot change the workout type during update.");
        }
        if (WorkoutType.WEIGHTLIFTING == workoutType && workoutFound instanceof Weightlifting entity && workoutDto instanceof WeightliftingDto dto) {
            entity.setWeightLifted(dto.getWeightLifted());
            entity.setSets(dto.getSets());
            entity.setReps(dto.getReps());
        } else if (WorkoutType.RUNNING == workoutType && workoutFound instanceof Running entity && workoutDto instanceof RunningDto dto) {
            entity.setCadence(dto.getCadence());
            entity.setRunningPace(dto.getRunningPace());
            entity.setMileSplits(dto.getMileSplits());
            entity.setElevation(dto.getElevation());
        } else if (WorkoutType.SWIMMING == workoutType && workoutFound instanceof Swimming entity && workoutDto instanceof SwimmingDto dto) {
            entity.setStrokeType(dto.getStrokeType());
            entity.setSwimmingPace(dto.getSwimmingPace());
        } else if (WorkoutType.YOGA == workoutType && workoutFound instanceof Yoga entity && workoutDto instanceof YogaDto dto) {
            entity.setFlexibilityGrade(dto.getFlexibilityGrade());
            entity.setStyle(dto.getStyle());
            entity.setStressLevels(dto.getStressLevels());
            entity.setBreath(dto.getBreath());
        } else {
            throw new IllegalArgumentException("Invalid DTO subtype provided for workout type: " + workoutType);
        }
        Workout saved = workoutRepository.save(workoutFound);
        return toDto(saved);
    }

    @Override
    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }

    public Workout toEntity(WorkoutDto workoutDto) {
        Workout workout;
        WorkoutType workoutType = workoutDto.getWorkoutType();
        if (WorkoutType.WEIGHTLIFTING == workoutType && workoutDto instanceof WeightliftingDto dto) {
            Weightlifting entity = new Weightlifting();
            entity.setWeightLifted(dto.getWeightLifted());
            entity.setSets(dto.getSets());
            entity.setReps(dto.getReps());
            workout = entity;
        } else if (WorkoutType.RUNNING == workoutType && workoutDto instanceof RunningDto dto) {
            Running entity = new Running();
            entity.setCadence(dto.getCadence());
            entity.setRunningPace(dto.getRunningPace());
            entity.setMileSplits(dto.getMileSplits());
            entity.setElevation(dto.getElevation());
            workout = entity;
        } else if (WorkoutType.SWIMMING == workoutType && workoutDto instanceof SwimmingDto dto) {
            Swimming entity = new Swimming();
            entity.setStrokeType(dto.getStrokeType());
            entity.setSwimmingPace(dto.getSwimmingPace());
            workout = entity;
        } else if (WorkoutType.YOGA == workoutType && workoutDto instanceof YogaDto dto) {
            Yoga entity = new Yoga();
            entity.setFlexibilityGrade(dto.getFlexibilityGrade());
            entity.setStyle(dto.getStyle());
            entity.setStressLevels(dto.getStressLevels());
            entity.setBreath(dto.getBreath());
            workout = entity;
        } else {
            throw new IllegalArgumentException("Invalid or unsupported workoutType");
        }
        workout.setStartTime(workoutDto.getStartTime());
        workout.setEndTime(workoutDto.getEndTime());
        workout.setCaloriesBurned(workoutDto.getCaloriesBurned());
        workout.setDistance(workoutDto.getDistance());
        workout.setWorkoutDate(workoutDto.getWorkoutDate());
        User user = userRepository.findById(workoutDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        workout.setUserId(user.getId());
        if (workoutDto.getSleepId() != null) {
            Sleep sleep = sleepRepository.findById(workoutDto.getSleepId())
                    .orElseThrow(() -> new EntityNotFoundException("Sleep record not found"));
            workout.setSleep(sleep);
        }
        List<Goal> goalList = goalRepository.findAllById(workoutDto.getContributingGoalIds());
        workout.setContributingGoals(new HashSet<>(goalList));

        Set<Long> nutritionFactIds = workoutDto.getLinkedNutritionIds();
        if (nutritionFactIds != null && !nutritionFactIds.isEmpty()) {
            List<NutritionFacts> factList = nutritionFactsRepository.findAllById(nutritionFactIds);
            if (factList.size() != nutritionFactIds.size()) {
                throw new EntityNotFoundException("One or more linked NutritionFact entities were not found.");
            }
            Set<NutritionFacts> nutritionFactSet = new HashSet<>(factList);
            workout.setLinkedNutrition(nutritionFactSet);
        }
        return workout;
    }

    public WorkoutDto toDto(Workout workout) {
        WorkoutDto workoutDto;
        if (workout instanceof Weightlifting) {
            Weightlifting entity = (Weightlifting) workout;
            WeightliftingDto dto = new WeightliftingDto();
            dto.setWorkoutType(WorkoutType.WEIGHTLIFTING);
            dto.setWeightLifted(entity.getWeightLifted());
            dto.setSets(entity.getSets());
            dto.setReps(entity.getReps());
            workoutDto = dto;
        } else if (workout instanceof Running) {
            Running entity = (Running) workout;
            RunningDto dto = new RunningDto();
            dto.setWorkoutType(WorkoutType.RUNNING);
            dto.setCadence(entity.getCadence());
            dto.setRunningPace(entity.getRunningPace());
            dto.setMileSplits(entity.getMileSplits());
            dto.setElevation(entity.getElevation());
            workoutDto = dto;
        } else if (workout instanceof Swimming) {
            Swimming entity = (Swimming) workout;
            SwimmingDto dto = new SwimmingDto();
            dto.setWorkoutType(WorkoutType.SWIMMING);
            dto.setStrokeType(entity.getStrokeType());
            dto.setSwimmingPace(entity.getSwimmingPace());
            workoutDto = dto;
        } else if (workout instanceof Yoga) {
            Yoga entity = (Yoga) workout;
            YogaDto dto = new YogaDto();
            dto.setWorkoutType(WorkoutType.YOGA);
            dto.setFlexibilityGrade(entity.getFlexibilityGrade());
            dto.setStyle(entity.getStyle());
            dto.setStressLevels(entity.getStressLevels());
            dto.setBreath(entity.getBreath());
            workoutDto = dto;
        } else {
            throw new IllegalArgumentException("Invalid or unsupported workoutType");
        }

        workoutDto.setStartTime(workout.getStartTime());
        workoutDto.setEndTime(workout.getEndTime());
        workoutDto.setCaloriesBurned(workout.getCaloriesBurned());
        workoutDto.setDistance(workout.getDistance());
        workoutDto.setWorkoutDate(workout.getWorkoutDate());

        workoutDto.setUserId(workout.getUserId());

        workoutDto.setSleepId(workout.getSleep().getId());

        Set<Long> goalIds = workout.getContributingGoals().stream()
                .map(Goal::getId)
                .collect(Collectors.toSet());
        workoutDto.setContributingGoalIds(goalIds);

        Set<Long> nutritionIds = workout.getLinkedNutrition().stream()
                .map(NutritionFacts::getId)
                .collect(Collectors.toSet());
        workoutDto.setLinkedNutritionIds(nutritionIds);
        return workoutDto;
    }
}