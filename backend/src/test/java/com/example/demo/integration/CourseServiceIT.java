package com.example.demo.integration;

import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CourseServiceIT{

    @Autowired
    CourseService service;

    @Test
    void createAndFetchCourse() {
        Course c = new Course(); c.setCourseName("Docker");
        Course saved = service.createCourse(c);

        Course fetched = service.getCourseById(saved.getId());
        assertThat(fetched.getCourseName()).isEqualTo("Docker");
    }
}
