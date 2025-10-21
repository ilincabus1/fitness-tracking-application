package com.example.FitnessTrackingApp.controller;

import com.example.FitnessTrackingApp.model.dto.SleepDto;
import com.example.FitnessTrackingApp.service.SleepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sleep")
public class SleepController {
    @Autowired
    SleepService sleepService;

    @PostMapping
    public ResponseEntity<SleepDto> save(@RequestBody SleepDto sleepDto) {
        SleepDto savedSleepDto = sleepService.saveSleep(sleepDto);
        return ResponseEntity.ok(savedSleepDto);
    }

    @GetMapping
    public ResponseEntity<List<SleepDto>> findAll() {
        List<SleepDto> sleepRecords = sleepService.findAll();
        return ResponseEntity.ok(sleepRecords);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SleepDto> findById(@PathVariable Long id) {
        return sleepService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SleepDto>> findAllSleepByUserId(@PathVariable Long userId) {
        List<SleepDto> sleepRecords = sleepService.findAllByUserId(userId);
        return ResponseEntity.ok(sleepRecords);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SleepDto> update(@PathVariable Long id, @RequestBody SleepDto sleepDto) {
        try {
            SleepDto updatedSleepDto = sleepService.updateSleep(id, sleepDto);
            return ResponseEntity.ok(updatedSleepDto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        sleepService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
