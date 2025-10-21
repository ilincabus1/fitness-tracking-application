package com.example.FitnessTrackingApp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Weightlifting workout details",
        allOf = {WorkoutDto.class}
)
public class WeightliftingDto extends WorkoutDto {
    private Double weightLifted;
    private Integer sets;
    private Integer reps;

}
