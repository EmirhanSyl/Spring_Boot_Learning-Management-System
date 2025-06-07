package com.example.demo.integration;

import com.example.demo.dto.EnrollRequest;
import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.service.CourseService;
import com.example.demo.service.LearningService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LearningServiceIT {

    @Autowired
    LearningService service;
    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;

    @Test
    void enrollCreatesLearningAndProgress() {
        User u = userService.createUser(new User());
        Course c = courseService.createCourse(new Course());

        EnrollRequest er = new EnrollRequest();
        er.setUserId(u.getId()); er.setCourseId(c.getId());

        service.enrollCourse(er);

        assertThat(service.getLearningCourses(u.getId()))
                .extracting(Course::getId).contains(c.getId());
    }
}
