package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.repository.CourseRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock  CourseRepository repo;
    @InjectMocks CourseService service;

    @Test
    @DisplayName("getCourseById → kurs varsa döner, yoksa null")
    void getCourseById() {
        Course c = new Course(); c.setId(10L);
        when(repo.findById(10L)).thenReturn(Optional.of(c));

        assertThat(service.getCourseById(10L)).isNotNull()
                .extracting(Course::getId)
                .isEqualTo(10L);
        assertThat(service.getCourseById(99L)).isNull();          // yok
    }

    @ParameterizedTest(name = "[{index}] mevcut fiyat={0} yeni fiyat={1}")
    @CsvSource({"100,150", "200,200"})
    @DisplayName("updateCourse → alanlar güncellenir, mevcut olmayan kurs null döner")
    void updateCourse_priceRules(int oldPrice, int newPrice) {
        Course oldC = new Course(); oldC.setId(5L);
        oldC.setCourseName("React"); oldC.setPrice(oldPrice);
        Course req = new Course();  req.setCourseName("React 19"); req.setPrice(newPrice);

        when(repo.findById(5L)).thenReturn(Optional.of(oldC));
        when(repo.save(any())).thenAnswer(a -> a.getArgument(0));

        Course updated = service.updateCourse(5L, req);

        assertThat(updated.getCourseName()).isEqualTo("React 19");
        assertThat(updated.getPrice()).isEqualTo(newPrice);
    }
}
