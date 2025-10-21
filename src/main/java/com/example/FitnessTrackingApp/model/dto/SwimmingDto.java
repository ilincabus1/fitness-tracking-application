package com.example.FitnessTrackingApp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Swimming workout details",
        allOf = {WorkoutDto.class}
)
public class SwimmingDto extends WorkoutDto {
    private Double swimmingPace;
    private String strokeType;

}
