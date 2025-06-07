package com.example.demo.service;

import com.example.demo.dto.ProgressRequest;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProgressServiceTest {

    @Mock ProgressRepository progressRepo;
    @Mock UserRepository userRepo;
    @Mock CourseRepository courseRepo;
    @InjectMocks ProgressService service;

    User user = new User(); Course course = new Course();

    @Test
    void updateProgress_happyPath() {
        Progress p = new Progress(); p.setPlayedTime(10); p.setDuration(120);
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(courseRepo.findById(101L)).thenReturn(Optional.of(course));
        when(progressRepo.findByUserAndCourse(user, course)).thenReturn(p);

        ProgressRequest req = new ProgressRequest();
        req.setUserId(1L); req.setCourseId(101L);
        req.setPlayedTime(30); req.setDuration(120);

        var resp = service.updateProgress(req);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(p.getPlayedTime()).isEqualTo(30);
    }

    @Test
    void updateProgress_lowerPlayedTime_rejected() {
        Progress p = new Progress(); p.setPlayedTime(50);
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(courseRepo.findById(101L)).thenReturn(Optional.of(course));
        when(progressRepo.findByUserAndCourse(user, course)).thenReturn(p);

        ProgressRequest req = new ProgressRequest();
        req.setUserId(1L); req.setCourseId(101L); req.setPlayedTime(20);

        var resp = service.updateProgress(req);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }
}
