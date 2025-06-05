package com.example.demo.controller;

import com.example.demo.dto.DiscussionRequest;
import com.example.demo.entity.Course;
import com.example.demo.entity.Discussion;
import com.example.demo.service.CourseService;
import com.example.demo.service.DiscussionService;
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

@WebMvcTest(DiscussionController.class)
class DiscussionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiscussionService discussionService;
    @MockBean
    private CourseService courseService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @ParameterizedTest
    @CsvSource("1")
    void getDiscussions(Long courseId) throws Exception {
        Course course = new Course();
        given(courseService.getCourseById(courseId)).willReturn(course);
        given(discussionService.getDiscussionsCourse(course)).willReturn(List.of());
        mockMvc.perform(get("/api/discussions/{courseId}", courseId))
                .andExpect(status().isOk());
    }

    static Stream<DiscussionRequest> requestProvider() {
        DiscussionRequest dr = new DiscussionRequest();
        dr.setCourse_id(1L);
        dr.setName("name");
        dr.setContent("hi");
        return Stream.of(dr);
    }

    @ParameterizedTest
    @MethodSource("requestProvider")
    void createDiscussion(DiscussionRequest dr) throws Exception {
        Course course = new Course();
        given(courseService.getCourseById(dr.getCourse_id())).willReturn(course);
        given(discussionService.createDiscussion(any(Discussion.class))).willReturn(new Discussion());

        mockMvc.perform(post("/api/discussions/addMessage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dr)))
                .andExpect(status().isCreated());
    }
}
