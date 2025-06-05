package com.example.demo.controller;

import com.example.demo.dto.EnrollRequest;
import com.example.demo.entity.Course;
import com.example.demo.entity.Learning;
import com.example.demo.service.LearningService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LearningController.class)
class LearningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LearningService learningService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @ParameterizedTest
    @CsvSource("1")
    void getLearningCourses(Long userId) throws Exception {
        given(learningService.getLearningCourses(userId)).willReturn(List.of());
        mockMvc.perform(get("/api/learning/{userId}", userId))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource("0")
    void getEnrollments(int dummy) throws Exception {
        given(learningService.getEnrollments()).willReturn(List.of());
        mockMvc.perform(get("/api/learning"))
                .andExpect(status().isOk());
    }

    static Stream<EnrollRequest> requestProvider() {
        EnrollRequest er = new EnrollRequest();
        er.setCourseId(1L);
        er.setUserId(1L);
        return Stream.of(er);
    }

    @ParameterizedTest
    @MethodSource("requestProvider")
    void enrollCourse(EnrollRequest request) throws Exception {
        given(learningService.enrollCourse(any(EnrollRequest.class))).willReturn("ok");

        mockMvc.perform(post("/api/learning")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource("1")
    void unenrollCourse(Long id) throws Exception {
        mockMvc.perform(delete("/api/learning/{id}", id))
                .andExpect(status().isOk());
    }
}
