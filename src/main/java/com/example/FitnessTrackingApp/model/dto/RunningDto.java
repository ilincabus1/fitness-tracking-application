package com.example.FitnessTrackingApp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Running workout details",
        allOf = {WorkoutDto.class}
)
public class RunningDto extends WorkoutDto {
    private Integer cadence;
    private Double runningPace;
    private Double elevation;
    private String mileSplits;
}
