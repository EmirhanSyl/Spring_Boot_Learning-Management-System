package com.example.demo.repository;

import com.example.demo.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CourseRepositoryTest {

    @Autowired CourseRepository repo;

    @Test
    void createAndRetrieve() {
        Course java = new Course(); java.setCourseName("Java");
        repo.save(java);

        assertThat(repo.findAll())
                .extracting(Course::getCourseName)
                .containsOnly("Java");
    }
}

