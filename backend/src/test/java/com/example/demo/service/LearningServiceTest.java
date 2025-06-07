package com.example.demo.service;

import com.example.demo.dto.EnrollRequest;
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
class LearningServiceTest {

    @Mock LearningRepository learningRepo;
    @Mock UserRepository userRepo;
    @Mock CourseRepository courseRepo;
    @Mock ProgressRepository progressRepo;
    @InjectMocks LearningService service;

    @Test
    @DisplayName("enrollCourse → başarı, Progress & Learning kayıtları oluşur")
    void enroll_success() {
        User u = new User(); u.setId(1L);
        Course c = new Course(); c.setId(202L);

        when(userRepo.findById(1L)).thenReturn(Optional.of(u));
        when(courseRepo.findById(202L)).thenReturn(Optional.of(c));
        when(learningRepo.findByUserAndCourse(u, c)).thenReturn(null);

        EnrollRequest req = new EnrollRequest();
        req.setUserId(1L); req.setCourseId(202L);

        String msg = service.enrollCourse(req);

        assertThat(msg).isEqualTo("Enrolled successfully");
        verify(progressRepo).save(any(Progress.class));
        verify(learningRepo).save(any(Learning.class));
    }

    @Test
    @DisplayName("enrollCourse → zaten kayıtlıysa uyarı mesajı")
    void enroll_duplicate() {
        User u = new User(); Course c = new Course();
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(u));
        when(courseRepo.findById(anyLong())).thenReturn(Optional.of(c));
        when(learningRepo.findByUserAndCourse(u, c))
                .thenReturn(new Learning());

        EnrollRequest req = new EnrollRequest();
        req.setUserId(1L); req.setCourseId(2L);

        assertThat(service.enrollCourse(req)).isEqualTo("Course already enrolled");
        verify(progressRepo, never()).save(any());
    }

    @Test
    @DisplayName("getLearningCourses → kullanıcının kurs listesi döner")
    void getLearningCourses() {
        Course java = new Course(); java.setId(101L);
        Learning l = new Learning(); l.setCourse(java);

        User u = new User();
        u.setLearningCourses(List.of(l));

        when(userRepo.findById(1L)).thenReturn(Optional.of(u));

        List<Course> list = service.getLearningCourses(1L);

        assertThat(list).extracting(Course::getId).containsExactly(101L);
    }
}
