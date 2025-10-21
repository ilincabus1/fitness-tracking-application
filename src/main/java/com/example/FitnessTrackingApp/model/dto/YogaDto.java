package com.example.FitnessTrackingApp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Yoga workout details",
        allOf = {WorkoutDto.class}
)
public class YogaDto extends WorkoutDto {
    private String style;
    private Integer flexibilityGrade;
    private Integer breath;
    private Integer stressLevels;

}
