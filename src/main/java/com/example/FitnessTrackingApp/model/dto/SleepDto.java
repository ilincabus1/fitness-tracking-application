package com.example.FitnessTrackingApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SleepDto {
    private LocalDate date;
    public String startTime;
    public String endTime;
    private Long userId;
}
