package com.example.demo.controller;

import com.example.demo.entity.Assessment;
import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.service.AssessmentService;
import com.example.demo.service.CourseService;
import com.example.demo.service.UserService;
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

@WebMvcTest(AssessmentController.class)
class AssessmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssessmentService assessmentService;
    @MockBean
    private UserService userService;
    @MockBean
    private CourseService courseService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @ParameterizedTest
    @CsvSource({"1,1"})
    void getAssessments(Long userId, Long courseId) throws Exception {
        User user = new User();
        Course course = new Course();
        given(userService.getUserById(userId)).willReturn(user);
        given(courseService.getCourseById(courseId)).willReturn(course);
        given(assessmentService.getAssessmentsByUserAndCourse(user, course)).willReturn(List.of());

        mockMvc.perform(get("/api/assessments/user/{userId}/course/{courseId}", userId, courseId))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource("1")
    void getPerformance(Long userId) throws Exception {
        User user = new User();
        given(userService.getUserById(userId)).willReturn(user);
        given(assessmentService.getAssessmentByUser(user)).willReturn(org.springframework.http.ResponseEntity.ok(List.of()));

        mockMvc.perform(get("/api/assessments/perfomance/{userId}", userId))
                .andExpect(status().isCreated());
    }

    static Stream<Assessment> assessmentProvider() {
        Assessment a = new Assessment();
        a.setMarks(10);
        return Stream.of(a);
    }

    @ParameterizedTest
    @MethodSource("assessmentProvider")
    void addAssessment(Assessment assessment) throws Exception {
        User user = new User();
        Course course = new Course();
        given(userService.getUserById(1L)).willReturn(user);
        given(courseService.getCourseById(1L)).willReturn(course);
        given(assessmentService.saveAssessment(any(User.class), any(Course.class), any(Assessment.class)))
                .willReturn(org.springframework.http.ResponseEntity.ok(assessment));

        mockMvc.perform(post("/api/assessments/add/{userId}/{courseId}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(assessment)))
                .andExpect(status().isOk());
    }
}
