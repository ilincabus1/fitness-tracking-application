package com.example.FitnessTrackingApp.service;

import com.example.FitnessTrackingApp.model.dto.SleepDto;

import java.util.List;
import java.util.Optional;

public interface SleepService {

    SleepDto saveSleep(SleepDto sleepDto);

    Optional<SleepDto> findById(Long id);

    List<SleepDto> findAll();

    void deleteById(Long id);

    SleepDto updateSleep(Long id, SleepDto sleepDto);

    List<SleepDto> findAllByUserId(Long userId);
}
