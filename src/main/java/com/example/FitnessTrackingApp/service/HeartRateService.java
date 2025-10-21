package com.example.FitnessTrackingApp.service;

import com.example.FitnessTrackingApp.model.dto.HeartRateDto;

import java.util.List;

public interface HeartRateService {
    HeartRateDto saveHeartRate(HeartRateDto heartRateDto);

    HeartRateDto findById(Long id);

    List<HeartRateDto> findAll();

    void deleteById(Long id);

    void deleteByWorkoutId(Long workoutId);

    public List<HeartRateDto> findByWorkoutIdDto(Long workoutId);
}
