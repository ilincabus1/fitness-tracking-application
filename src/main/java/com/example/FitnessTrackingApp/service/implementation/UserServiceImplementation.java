package com.example.FitnessTrackingApp.service.implementation;

import com.example.FitnessTrackingApp.model.dto.UserDto;
import com.example.FitnessTrackingApp.model.entities.User;
import com.example.FitnessTrackingApp.repo.UserRepository;
import com.example.FitnessTrackingApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(UserDto dto) {
        String email = dto.getEmail();
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("E-mail address is not valid: it must contain character '@'.");
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setGender(dto.getGender());
        user.setHeight(dto.getHeight());
        user.setWeight(dto.getWeight());
        double newBmi = calculateBmi(dto.getWeight(), dto.getHeight());
        user.setBmi(newBmi);
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long id, UserDto dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setGender(dto.getGender());
        user.setHeight(dto.getHeight());
        user.setWeight(dto.getWeight());
        double newBmi = calculateBmi(dto.getWeight(), dto.getHeight());
        user.setBmi(newBmi);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public double calculateBmiForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Double weight = user.getWeight();
        Double height = user.getHeight();
        return this.calculateBmi(weight, height);
    }

    public double calculateBmi(double weight, double height) {
        double bmi = weight / (height * height);
        return Math.round(bmi * 100.0) / 100.0;
    }
}

