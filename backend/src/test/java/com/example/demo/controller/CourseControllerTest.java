package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @ParameterizedTest
    @CsvSource("1")
    void getCourse(Long id) throws Exception {
        Course c = new Course();
        c.setId(id);
        given(courseService.getCourseById(id)).willReturn(c);

        mockMvc.perform(get("/api/courses/{id}", id))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource("0")
    void getAllCourses(int dummy) throws Exception {
        given(courseService.getAllCourses()).willReturn(java.util.List.of());
        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk());
    }

    static Stream<Course> courseProvider() {
        Course c = new Course();
        c.setCourseName("Name");
        return Stream.of(c);
    }

    @ParameterizedTest
    @MethodSource("courseProvider")
    void createCourse(Course c) throws Exception {
        given(courseService.createCourse(any(Course.class))).willReturn(c);

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(c)))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource("1")
    void updateCourse(Long id) throws Exception {
        Course c = new Course();
        given(courseService.updateCourse(eq(id), any(Course.class))).willReturn(c);

        mockMvc.perform(post("/api/courses/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(c)))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource("1")
    void deleteCourse(Long id) throws Exception {
        mockMvc.perform(delete("/api/courses/{id}", id))
                .andExpect(status().isOk());
    }
}
