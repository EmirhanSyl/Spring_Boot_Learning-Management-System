package com.example.demo.repository;

import com.example.demo.entity.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class LearningRepositoryTest {

    @Autowired LearningRepository repo;
    @Autowired TestEntityManager em;

    @Test
    void findByUserAndCourse() {
        User u = em.persist(new User());
        Course c = em.persist(new Course());
        Learning l = new Learning(); l.setUser(u); l.setCourse(c);
        em.persist(l);

        assertThat(repo.findByUserAndCourse(u, c)).isEqualTo(l);
    }
}
