package com.example.FitnessTrackingApp.service.implementation;

import com.example.FitnessTrackingApp.model.dto.SleepDto;
import com.example.FitnessTrackingApp.model.entities.Sleep;
import com.example.FitnessTrackingApp.model.entities.User;
import com.example.FitnessTrackingApp.repo.SleepRepository;
import com.example.FitnessTrackingApp.repo.UserRepository;
import com.example.FitnessTrackingApp.service.SleepService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SleepServiceImplementation implements SleepService {
    @Autowired
    SleepRepository sleepRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public SleepDto saveSleep(SleepDto sleepDto) {
        User user = userRepository.findById(sleepDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + sleepDto.getUserId()));
        Sleep sleep = new Sleep();
        sleep.setDate(sleepDto.getDate());
        sleep.setStartTime(sleepDto.getStartTime());
        sleep.setEndTime(sleepDto.getEndTime());
        sleep.setUserId(user.getId());
        Sleep savedSleep = sleepRepository.save(sleep);
        return convertToDto(savedSleep);
    }

    @Override
    public Optional<SleepDto> findById(Long id) {
        Optional<Sleep> sleepOptional = sleepRepository.findById(id);
        return sleepOptional
                .map(this::convertToDto);
    }

    @Override
    public List<SleepDto> findAll() {
        List<Sleep> sleepEntities = sleepRepository.findAll();
        return sleepEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        sleepRepository.deleteById(id);
    }

    @Override
    public SleepDto updateSleep(Long id, SleepDto sleepDto) {
        Sleep existingSleep = sleepRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sleep record not found with ID: " + id));
        existingSleep.setStartTime(sleepDto.getStartTime());
        existingSleep.setEndTime(sleepDto.getEndTime());
//        existingSleep.setId(sleepDto.getId());
        existingSleep.setDate(sleepDto.getDate());
        User user = userRepository.findById(sleepDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingSleep.setUserId(user.getId());
        Sleep savedSleep = sleepRepository.save(existingSleep);
        return convertToDto(savedSleep);
    }

    private SleepDto convertToDto(Sleep sleep) {
        SleepDto dto = new SleepDto();
        dto.setDate(sleep.getDate());
        dto.setStartTime(sleep.getStartTime());
        dto.setEndTime(sleep.getEndTime());
        if (sleep.getUserId() != null) {
            dto.setUserId(sleep.getUserId());
        }
        return dto;
    }

    @Override
    public List<SleepDto> findAllByUserId(Long userId) {
        List<Sleep> sleepEntities = sleepRepository.findByUserId(userId);
        return sleepEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
