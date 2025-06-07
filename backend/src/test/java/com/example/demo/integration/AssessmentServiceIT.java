package com.example.demo.integration;

import com.example.demo.entity.Assessment;
import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.service.AssessmentService;
import com.example.demo.service.CourseService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AssessmentServiceIT{

    @Autowired
    AssessmentService service;
    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;

    @Test
    void higherScoreUpdates() {
        User user   = userService.createUser(new User());
        Course course = courseService.createCourse(new Course());

        service.saveAssessment(user, course, new Assessment());
        var resp = service.saveAssessment(user, course, new Assessment());

        assertThat(resp.getBody().getMarks()).isEqualTo(90);
    }
}
