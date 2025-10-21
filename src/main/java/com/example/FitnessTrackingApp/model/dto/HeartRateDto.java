package com.example.FitnessTrackingApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeartRateDto {
    private LocalDateTime timestamp;
    private double beatsPerMinute;
    private Long workoutId;
}
