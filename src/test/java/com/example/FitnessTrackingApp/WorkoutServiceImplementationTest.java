package com.example.FitnessTrackingApp;

import com.example.FitnessTrackingApp.model.dto.WeightliftingDto;
import com.example.FitnessTrackingApp.model.dto.WorkoutDto;
import com.example.FitnessTrackingApp.model.entities.*;
import com.example.FitnessTrackingApp.model.enums.WorkoutType;
import com.example.FitnessTrackingApp.repo.*;
import com.example.FitnessTrackingApp.service.implementation.WorkoutServiceImplementation;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkoutServiceImplementationTest {
    @Mock
    private WorkoutRepository workoutRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SleepRepository sleepRepository;
    @Mock
    private GoalRepository goalRepository;
    @Mock
    private NutritionFactsRepository nutritionFactsRepository;

    @InjectMocks
    private WorkoutServiceImplementation workoutService;

    private static final Long USER_ID = 1L;
    private static final Long WORKOUT_ID = 10L;
    private static final Long SLEEP_ID = 20L;
    private static final Long GOAL_ID_1 = 30L;
    private static final Long NUTRITION_ID_1 = 40L;

    private User mockUser;
    private Sleep mockSleep;
    private Goal mockGoal;
    private NutritionFacts mockNutrition;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(USER_ID);

        mockSleep = new Sleep();
        mockSleep.setId(SLEEP_ID);

        mockGoal = new Goal();
        mockGoal.setId(GOAL_ID_1);

        mockNutrition = new NutritionFacts();
        mockNutrition.setId(NUTRITION_ID_1);
    }

    @Test
    void createWorkout_shouldSaveAndReturnDto() {
        WeightliftingDto inputDto = new WeightliftingDto();
        inputDto.setWorkoutType(WorkoutType.WEIGHTLIFTING);
        inputDto.setUserId(USER_ID);
        inputDto.setWeightLifted(100.0);
        inputDto.setSets(5);
        inputDto.setReps(5);
        inputDto.setSleepId(SLEEP_ID);
        inputDto.setContributingGoalIds(Set.of(GOAL_ID_1));
        inputDto.setLinkedNutritionIds(Set.of(NUTRITION_ID_1));
        inputDto.setCaloriesBurned(500.0);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
        when(sleepRepository.findById(SLEEP_ID)).thenReturn(Optional.of(mockSleep));
        when(goalRepository.findAllById(anySet())).thenReturn(List.of(mockGoal));
        when(nutritionFactsRepository.findAllById(anySet())).thenReturn(List.of(mockNutrition));

        Weightlifting savedEntity = new Weightlifting();
        savedEntity.setId(WORKOUT_ID);
        savedEntity.setWeightLifted(inputDto.getWeightLifted());
        savedEntity.setSets(inputDto.getSets());
        savedEntity.setReps(inputDto.getReps());
        savedEntity.setUserId(USER_ID);
        savedEntity.setSleep(mockSleep);
        savedEntity.setContributingGoals(Set.of(mockGoal));
        savedEntity.setLinkedNutrition(Set.of(mockNutrition));
        savedEntity.setCaloriesBurned(inputDto.getCaloriesBurned());

        when(workoutRepository.save(any(Workout.class))).thenReturn(savedEntity);

        WorkoutDto resultDto = workoutService.createWorkout(inputDto);

        verify(userRepository, times(1)).findById(USER_ID);
        verify(sleepRepository, times(1)).findById(SLEEP_ID);
        verify(goalRepository, times(1)).findAllById(Set.of(GOAL_ID_1));
        verify(nutritionFactsRepository, times(1)).findAllById(Set.of(NUTRITION_ID_1));
        verify(workoutRepository, times(1)).save(any(Workout.class));

        assertThat(resultDto).isNotNull();
        assertThat(resultDto).isInstanceOf(WeightliftingDto.class);
        WeightliftingDto resultWeightliftingDto = (WeightliftingDto) resultDto;
        assertThat(resultWeightliftingDto.getUserId()).isEqualTo(USER_ID);
        assertThat(resultWeightliftingDto.getWorkoutType()).isEqualTo(WorkoutType.WEIGHTLIFTING);
        assertThat(resultWeightliftingDto.getWeightLifted()).isEqualTo(100.0);
        assertThat(resultWeightliftingDto.getSleepId()).isEqualTo(SLEEP_ID);
        assertThat(resultWeightliftingDto.getContributingGoalIds()).containsExactly(GOAL_ID_1);
        assertThat(resultWeightliftingDto.getLinkedNutritionIds()).containsExactly(NUTRITION_ID_1);
    }

    @Test
    void createWorkout_shouldThrowException_forMismatchedDtoType() {
        WeightliftingDto mismatchedDto = new WeightliftingDto();
        mismatchedDto.setWorkoutType(WorkoutType.YOGA);
        mismatchedDto.setUserId(USER_ID);
        assertThrows(
                IllegalArgumentException.class,
                () -> workoutService.createWorkout(mismatchedDto),
                "Expected IllegalArgumentException for mismatched DTO class and WorkoutType"
        );
        verify(workoutRepository, never()).save(any());
    }

    @Test
    void createWorkout_shouldThrowException_whenSleepRecordNotFound() {
        final Long NON_EXISTENT_SLEEP_ID = 999L;
        WeightliftingDto inputDto = new WeightliftingDto();
        inputDto.setWorkoutType(WorkoutType.WEIGHTLIFTING);
        inputDto.setUserId(USER_ID);
        inputDto.setWeightLifted(100.0);
        inputDto.setSleepId(NON_EXISTENT_SLEEP_ID);
        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.of(mockUser));
        when(sleepRepository.findById(NON_EXISTENT_SLEEP_ID))
                .thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> workoutService.createWorkout(inputDto),
                "Expected EntityNotFoundException when sleep record is not found"
        );
        assertThat(exception.getMessage()).isEqualTo("Sleep record not found");
        verify(userRepository, times(1)).findById(USER_ID);
        verify(sleepRepository, times(1)).findById(NON_EXISTENT_SLEEP_ID);
        verify(workoutRepository, never()).save(any());
    }
}
