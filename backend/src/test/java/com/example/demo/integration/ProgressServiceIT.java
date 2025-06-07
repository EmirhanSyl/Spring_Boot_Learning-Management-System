package com.example.demo.integration;

import com.example.demo.dto.ProgressRequest;
import com.example.demo.entity.Course;
import com.example.demo.entity.Progress;
import com.example.demo.entity.User;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.service.CourseService;
import com.example.demo.service.ProgressService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProgressServiceIT {

    @Autowired
    ProgressService progressService;
    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @Autowired
    ProgressRepository repo;

    @Test
    void updateProgress() {
        User u = userService.createUser(new User());
        Course c = courseService.createCourse(new Course());
        repo.save(new Progress());

        ProgressRequest pr = new ProgressRequest();
        pr.setUserId(u.getId()); pr.setCourseId(c.getId());
        pr.setPlayedTime(45); pr.setDuration(120);

        progressService.updateProgress(pr);

        assertThat(progressService.getProgress(u.getId(), c.getId()))
                .isEqualTo(45);
    }
}
