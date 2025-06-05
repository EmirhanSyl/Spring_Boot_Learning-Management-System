package com.example.demo.controller;

import com.example.demo.dto.ProgressRequest;
import com.example.demo.service.ProgressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.ResponseEntity;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProgressController.class)
class ProgressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProgressService progressService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @ParameterizedTest
    @CsvSource({"1,1"})
    void getProgress(Long userId, Long courseId) throws Exception {
        given(progressService.getProgress(userId, courseId)).willReturn(0f);
        mockMvc.perform(get("/api/progress/{userId}/{courseId}", userId, courseId))
                .andExpect(status().isOk());
    }

    static Stream<ProgressRequest> requestProvider() {
        ProgressRequest pr = new ProgressRequest();
        pr.setUserId(1L);
        pr.setCourseId(1L);
        pr.setPlayedTime(1f);
        pr.setDuration(10f);
        return Stream.of(pr);
    }

    @ParameterizedTest
    @MethodSource("requestProvider")
    void updateProgress(ProgressRequest request) throws Exception {
        given(progressService.updateProgress(any(ProgressRequest.class))).willReturn(ResponseEntity.ok(""));

        mockMvc.perform(put("/api/progress/update-progress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("requestProvider")
    void updateDuration(ProgressRequest request) throws Exception {
        given(progressService.updateDuration(any(ProgressRequest.class))).willReturn(ResponseEntity.ok(""));

        mockMvc.perform(put("/api/progress/update-duration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
