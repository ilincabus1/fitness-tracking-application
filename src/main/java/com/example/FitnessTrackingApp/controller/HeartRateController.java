package com.example.FitnessTrackingApp.controller;

import com.example.FitnessTrackingApp.model.dto.HeartRateDto;
import com.example.FitnessTrackingApp.service.HeartRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/heartrates")
public class HeartRateController {
    @Autowired
    HeartRateService heartRateService;

    @PostMapping
    public ResponseEntity<HeartRateDto> save(@RequestBody HeartRateDto heartRateDto) {
        HeartRateDto savedHeartRate = heartRateService.saveHeartRate(heartRateDto);
        return ResponseEntity.ok(savedHeartRate);
    }

    @GetMapping
    public ResponseEntity<List<HeartRateDto>> findAll() {
        List<HeartRateDto> heartRatesDto = heartRateService.findAll();
        return ResponseEntity.ok(heartRatesDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HeartRateDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(heartRateService.findById(id));
    }

    @GetMapping("/workout/{workoutId}")
    public ResponseEntity<List<HeartRateDto>> findByWorkoutId(@PathVariable Long workoutId) {
        List<HeartRateDto> heartRateDtos = heartRateService.findByWorkoutIdDto(workoutId);
        if (heartRateDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(heartRateDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        heartRateService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/workout/{workoutId}")
    public ResponseEntity<Void> deleteByWorkoutId(@PathVariable Long workoutId) {
        heartRateService.deleteByWorkoutId(workoutId);
        return ResponseEntity.noContent().build();
    }
}
