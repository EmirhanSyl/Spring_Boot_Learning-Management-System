package com.example.demo.controller;

import com.example.demo.dto.FeedbackRequest;
import com.example.demo.entity.Feedback;
import com.example.demo.service.FeedbackService;
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

@WebMvcTest(FeedbackController.class)
class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService feedbackService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @ParameterizedTest
    @CsvSource("1")
    void getFeedbacks(Long courseId) throws Exception {
        given(feedbackService.getFeedbacksForCourse(courseId)).willReturn(List.of());
        mockMvc.perform(get("/api/feedbacks/{courseId}", courseId))
                .andExpect(status().isOk());
    }

    static Stream<FeedbackRequest> requestProvider() {
        FeedbackRequest fr = new FeedbackRequest();
        fr.setCourse_id(1L);
        fr.setComment("Nice");
        return Stream.of(fr);
    }

    @ParameterizedTest
    @MethodSource("requestProvider")
    void submitFeedback(FeedbackRequest request) throws Exception {
        given(feedbackService.submitFeedback(any(FeedbackRequest.class))).willReturn("ok");

        mockMvc.perform(post("/api/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
