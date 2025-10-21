package com.example.FitnessTrackingApp.service;

import com.example.FitnessTrackingApp.model.dto.UserDto;
import com.example.FitnessTrackingApp.model.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(UserDto dto);

    List<User> findAllUsers();

    Optional<User> findUserById(Long id);

    User updateUser(Long id, UserDto dto);

    void deleteUser(Long id);

    public double calculateBmiForUser(Long userId);
}
