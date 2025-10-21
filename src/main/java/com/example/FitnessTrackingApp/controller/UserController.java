package com.example.FitnessTrackingApp.controller;

import com.example.FitnessTrackingApp.model.dto.UserDto;
import com.example.FitnessTrackingApp.model.entities.User;
import com.example.FitnessTrackingApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/bmi")
    public ResponseEntity<Double> getBmiForUser(@PathVariable Long id) {
        double bmi = userService.calculateBmiForUser(id);
        return ResponseEntity.ok(bmi);
    }
}
