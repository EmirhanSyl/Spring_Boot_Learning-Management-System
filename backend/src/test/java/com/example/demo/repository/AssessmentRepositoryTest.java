package com.example.demo.repository;

import com.example.demo.entity.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")      // application-test.properties’i okuyacak
class AssessmentRepositoryTest {

    @Autowired AssessmentRepository repo;
    @Autowired TestEntityManager em;

    @Test
    void findByUserAndCourse() {
        User u = em.persist(new User());
        Course c = em.persist(new Course());
        Assessment a = new Assessment(); a.setUser(u); a.setCourse(c); a.setMarks(75);
        em.persist(a);

        assertThat(repo.findByUserAndCourse(u, c))
                .singleElement()
                .isEqualTo(a);
    }
}
