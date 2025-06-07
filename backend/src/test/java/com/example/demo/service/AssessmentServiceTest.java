package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.AssessmentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssessmentServiceTest {

    @Mock  AssessmentRepository repo;
    @InjectMocks AssessmentService service;

    User user = new User();
    Course course = new Course();

    @Test
    @DisplayName("İlk kez kayıt → CREATED")
    void saveFirstAssessment() {
        Assessment a = new Assessment(); a.setMarks(70);
        when(repo.findByUserAndCourse(user, course))
                .thenReturn(Collections.emptyList());
        when(repo.save(any())).thenReturn(a);

        var resp = service.saveAssessment(user, course, a);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(repo).save(a);
    }

    @Test
    @DisplayName("Yeni not eski nottan büyükse güncellenir")
    void overwriteWithHigherScore() {
        Assessment old = new Assessment(); old.setMarks(60);
        Assessment incoming = new Assessment(); incoming.setMarks(90);

        when(repo.findByUserAndCourse(user, course)).thenReturn(java.util.List.of(old));
        when(repo.save(any())).thenReturn(old);

        var resp = service.saveAssessment(user, course, incoming);

        assertThat(resp.getBody().getMarks()).isEqualTo(90);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Daha düşük not reddedilir → BAD_REQUEST")
    void rejectLowerScore() {
        Assessment old = new Assessment();
        old.setMarks(85);
        Assessment incoming = new Assessment();
        incoming.setMarks(80);
        when(repo.findByUserAndCourse(user, course)).thenReturn(java.util.List.of(old));

        var resp = service.saveAssessment(user, course, incoming);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(repo, never()).save(any());
    }
}
