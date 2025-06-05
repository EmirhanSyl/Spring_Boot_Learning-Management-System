package com.example.demo.controller;

import com.example.demo.dto.QuestionRequest;
import com.example.demo.entity.Course;
import com.example.demo.entity.Questions;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.QuestionRepository;
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
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuestionController.class)
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionRepository questionRepository;
    @MockBean
    private CourseRepository courseRepository;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static Stream<QuestionRequest> requestProvider() {
        QuestionRequest qr = new QuestionRequest();
        qr.setCourseId(1L);
        qr.setQuestion("q");
        qr.setOption1("a");
        qr.setOption2("b");
        qr.setOption3("c");
        qr.setOption4("d");
        qr.setAnswer("a");
        return Stream.of(qr);
    }

    @ParameterizedTest
    @MethodSource("requestProvider")
    void addQuestion(QuestionRequest qr) throws Exception {
        given(courseRepository.findById(qr.getCourseId())).willReturn(Optional.of(new Course()));
        given(questionRepository.save(any(Questions.class))).willReturn(new Questions());

        mockMvc.perform(post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(qr)))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @CsvSource("1")
    void getQuestions(Long courseId) throws Exception {
        given(courseRepository.findById(courseId)).willReturn(Optional.of(new Course()));
        given(questionRepository.findByCourse(any(Course.class))).willReturn(List.of());

        mockMvc.perform(get("/api/questions/{courseId}", courseId))
                .andExpect(status().isOk());
    }
}
