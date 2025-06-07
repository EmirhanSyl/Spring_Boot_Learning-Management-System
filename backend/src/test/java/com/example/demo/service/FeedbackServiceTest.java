package com.example.demo.service;

import com.example.demo.dto.FeedbackRequest;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @Mock FeedbackRepository feedbackRepo;
    @Mock CourseRepository courseRepo;
    @InjectMocks FeedbackService service;

    @Test
    void getFeedbacksForCourse_returnsLinkedFeedback() {
        Course c = new Course();
        Feedback f = new Feedback(); f.setComment("Nice!");
        c.setFeedbacks(List.of(f));

        when(courseRepo.findById(101L)).thenReturn(Optional.of(c));

        assertThat(service.getFeedbacksForCourse(101L))
                .hasSize(1)
                .first().extracting(Feedback::getComment).isEqualTo("Nice!");
    }

    @Test
    void submitFeedback_success() {
        Course c = new Course(); c.setId(102L);
        when(courseRepo.findById(102L)).thenReturn(Optional.of(c));
        when(feedbackRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        FeedbackRequest fr = new FeedbackRequest();
        fr.setCourse_id(102L); fr.setComment("Harika içerik");

        assertThat(service.submitFeedback(fr))
                .isEqualTo("feedback submitted successfully");
    }
}
