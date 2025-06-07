package com.example.demo.integration;

import com.example.demo.dto.FeedbackRequest;
import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;
import com.example.demo.service.FeedbackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class FeedbackServiceIT {

    @Autowired
    FeedbackService feedbackService;
    @Autowired CourseService courseService;

    @Test
    void submitFeedback() {
        Course c = courseService.createCourse(new Course());

        FeedbackRequest fr = new FeedbackRequest();
        fr.setCourse_id(c.getId()); fr.setComment("Süper");
        feedbackService.submitFeedback(fr);

        assertThat(feedbackService.getFeedbacksForCourse(c.getId()))
                .singleElement()
                .extracting("comment").isEqualTo("Süper");
    }
}
