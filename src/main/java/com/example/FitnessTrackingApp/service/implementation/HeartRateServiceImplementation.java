package com.example.FitnessTrackingApp.service.implementation;

import com.example.FitnessTrackingApp.model.dto.HeartRateDto;
import com.example.FitnessTrackingApp.model.entities.HeartRate;
import com.example.FitnessTrackingApp.model.entities.Workout;
import com.example.FitnessTrackingApp.repo.HeartRateRepository;
import com.example.FitnessTrackingApp.repo.WorkoutRepository;
import com.example.FitnessTrackingApp.service.HeartRateService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HeartRateServiceImplementation implements HeartRateService {
    @Autowired
    HeartRateRepository heartRateRepository;

    @Autowired
    WorkoutRepository workoutRepository;

    @Override
    public HeartRateDto saveHeartRate(HeartRateDto heartRateDto) {
        Workout workout = workoutRepository.findById(heartRateDto.getWorkoutId()).orElseThrow(() -> new RuntimeException("Workout not found"));
        HeartRate heartRate = new HeartRate();
        heartRate.setTimestamp(heartRateDto.getTimestamp());
        heartRate.setBeatsPerMinute(heartRateDto.getBeatsPerMinute());
        heartRate.setWorkout(workout);
        HeartRate saved = heartRateRepository.save(heartRate);
        HeartRateDto response = new HeartRateDto();
        response.setWorkoutId(saved.getWorkout().getId());
        response.setTimestamp(saved.getTimestamp());
        response.setBeatsPerMinute(saved.getBeatsPerMinute());
        return response;
    }

    @Override
    public HeartRateDto findById(Long id) {
        HeartRate hr = heartRateRepository.findById(id).orElseThrow(() -> new RuntimeException("Heart rate log not found"));
        return toDto(hr);
    }

    @Override
    public List<HeartRateDto> findAll() {
        return heartRateRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        heartRateRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByWorkoutId(Long workoutId) {
        heartRateRepository.deleteByWorkoutId(workoutId);
    }

    public HeartRateDto toDto(HeartRate heartRate) {
        HeartRateDto dto = new HeartRateDto();
        dto.setTimestamp(heartRate.getTimestamp());
        dto.setBeatsPerMinute(heartRate.getBeatsPerMinute());
        if (heartRate.getWorkout() != null) {
            dto.setWorkoutId(heartRate.getWorkout().getId());
        }
        return dto;
    }

    @Override
    public List<HeartRateDto> findByWorkoutIdDto(Long workoutId) {
        List<HeartRate> heartRates = heartRateRepository.findByWorkoutId(workoutId);
        return heartRates.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
