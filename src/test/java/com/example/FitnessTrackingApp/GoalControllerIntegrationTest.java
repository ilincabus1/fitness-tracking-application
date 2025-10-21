package com.example.FitnessTrackingApp;

import com.example.FitnessTrackingApp.model.dto.GoalDto;
import com.example.FitnessTrackingApp.model.entities.User;
import com.example.FitnessTrackingApp.model.enums.GoalStatus;
import com.example.FitnessTrackingApp.repo.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {"spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"}, classes = FitnessTrackingAppApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class GoalControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existingUserId;
    private final String API_URL = "/api/goals";

    @BeforeEach
    void setup() {
        User user = new User();
        user.setName("Goal Tester");
        user.setEmail("goal@test.com");
        User savedUser = userRepository.save(user);
        existingUserId = savedUser.getId();
    }

    @Test
    void createGoal_withValidUserId_shouldReturnStatus200AndGoalDto() throws Exception {
        GoalDto inputDto = new GoalDto(
                100.0,
                10000,
                5.0,
                existingUserId,
                GoalStatus.ACTIVE
        );
        String requestJson = objectMapper.writeValueAsString(inputDto);
        ResultActions resultActions = mockMvc.perform(post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.noKm").value(100.0))
                .andExpect(jsonPath("$.dailySteps").value(10000))
                .andExpect(jsonPath("$.userId").value(existingUserId))
                .andExpect(jsonPath("$.status").value(GoalStatus.ACTIVE.name()));
    }

    @Test
    void createGoal_WithInvalidUserId_ShouldThrowServletException() throws Exception {
        final Long nonExistentUserId = -999L;
        GoalDto inputDto = new GoalDto(
                50.0,
                5000,
                10.0,
                nonExistentUserId,
                GoalStatus.ACTIVE
        );
        String requestJson = objectMapper.writeValueAsString(inputDto);
        assertThrows(ServletException.class, () ->
                mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
        );
    }
}
