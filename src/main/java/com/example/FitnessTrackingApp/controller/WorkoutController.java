package com.example.FitnessTrackingApp.controller;

import com.example.FitnessTrackingApp.model.dto.*;
import com.example.FitnessTrackingApp.model.entities.Workout;
import com.example.FitnessTrackingApp.model.enums.WorkoutType;
import com.example.FitnessTrackingApp.service.WorkoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {
    @Autowired
    WorkoutService workoutService;

    @GetMapping("/{workoutId}")
    public WorkoutDto findWorkoutById(@PathVariable Long id) {
        Workout workoutEntity = workoutService.findWorkoutById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workout not found"));
        return workoutService.toDto(workoutEntity);
    }

    @PostMapping("/running")
    @Operation(summary = "Create a new Running workout")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully created Running workout",
            content = @Content(
                    schema = @Schema(implementation = RunningDto.class)
            )
    )
    public ResponseEntity<RunningDto> createRunningWorkout(@RequestBody RunningDto runningDto) {
        runningDto.setWorkoutType(WorkoutType.RUNNING);
        WorkoutDto savedWorkoutDto1 = workoutService.createWorkout(runningDto);
        RunningDto runningResponse = (RunningDto) savedWorkoutDto1;
        return ResponseEntity.ok(runningResponse);
    }

    @PostMapping("/swimming")
    @Operation(summary = "Create a new Swimming workout")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully created Swimming workout",
            content = @Content(
                    schema = @Schema(implementation = SwimmingDto.class)
            )
    )
    public ResponseEntity<SwimmingDto> createSwimmingWorkout(@RequestBody SwimmingDto swimmingDto) {
        swimmingDto.setWorkoutType(WorkoutType.SWIMMING);
        WorkoutDto savedWorkoutDto2 = workoutService.createWorkout(swimmingDto);
        SwimmingDto swimmingResponse = (SwimmingDto) savedWorkoutDto2;
        return ResponseEntity.ok(swimmingResponse);
    }

    @PostMapping("/yoga")
    @Operation(summary = "Create a new Yoga workout")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully created Yoga workout",
            content = @Content(
                    schema = @Schema(implementation = YogaDto.class)
            )
    )
    public ResponseEntity<YogaDto> createYogaWorkout(@RequestBody YogaDto yogaDto) {
        yogaDto.setWorkoutType(WorkoutType.YOGA);
        WorkoutDto savedWorkoutDto3 = workoutService.createWorkout(yogaDto);
        YogaDto yogaResponse = (YogaDto) savedWorkoutDto3;
        return ResponseEntity.ok(yogaResponse);
    }

    @PostMapping("/weightlifting")
    @Operation(summary = "Create a new Weightlifting workout")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully created Weightlifting workout",
            content = @Content(
                    schema = @Schema(implementation = WeightliftingDto.class)
            )
    )
    public ResponseEntity<WeightliftingDto> createWeightliftingWorkout(@RequestBody WeightliftingDto weightliftingDto) {
        weightliftingDto.setWorkoutType(WorkoutType.WEIGHTLIFTING);
        WorkoutDto savedWorkoutDto4 = workoutService.createWorkout(weightliftingDto);
        WeightliftingDto weightliftingResponse = (WeightliftingDto) savedWorkoutDto4;
        return ResponseEntity.ok(weightliftingResponse);
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<WorkoutDto>> getAllWorkouts() {
        return ResponseEntity.ok(workoutService.findAllWorkoutsDto());
    }

    @PutMapping("/weightlifting/{workoutId}")
    public ResponseEntity<WeightliftingDto> updateWeightliftingWorkout(
            @PathVariable Long id,
            @RequestBody WeightliftingDto weightliftingDto) {
        WorkoutDto updatedWorkout = workoutService.updateWorkout(id, weightliftingDto);
        if (updatedWorkout instanceof WeightliftingDto resultDto) {
            return ResponseEntity.ok(resultDto);
        }
        throw new RuntimeException("Updated workout type mismatch.");
    }

    @PutMapping("/running/{workoutId}")
    public ResponseEntity<RunningDto> updateRunningWorkout(
            @PathVariable Long id,
            @RequestBody RunningDto runningDto) {
        WorkoutDto updatedWorkout = workoutService.updateWorkout(id, runningDto);
        if (updatedWorkout instanceof RunningDto resultDto) {
            return ResponseEntity.ok(resultDto);
        }
        throw new RuntimeException("Updated workout type mismatch.");
    }

    @PutMapping("/swimming/{workoutId}")
    public ResponseEntity<SwimmingDto> updateSwimmingWorkout(
            @PathVariable Long id,
            @RequestBody SwimmingDto swimmingDto) {
        WorkoutDto updatedWorkout = workoutService.updateWorkout(id, swimmingDto);
        if (updatedWorkout instanceof SwimmingDto resultDto) {
            return ResponseEntity.ok(resultDto);
        }
        throw new RuntimeException("Updated workout type mismatch.");
    }

    @PutMapping("/yoga/{workoutId}")
    public ResponseEntity<YogaDto> updateYogaWorkout(
            @PathVariable Long id,
            @RequestBody YogaDto yogaDto) {
        WorkoutDto updatedWorkout = workoutService.updateWorkout(id, yogaDto);
        if (updatedWorkout instanceof YogaDto resultDto) {
            return ResponseEntity.ok(resultDto);
        }
        throw new RuntimeException("Updated workout type mismatch.");
    }

}
