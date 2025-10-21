package com.example.FitnessTrackingApp.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "heart_rates")
@AllArgsConstructor
@NoArgsConstructor
public class HeartRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    private double beatsPerMinute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;
}
