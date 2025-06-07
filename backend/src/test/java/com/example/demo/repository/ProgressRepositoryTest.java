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
class ProgressRepositoryTest {

    @Autowired ProgressRepository repo;
    @Autowired TestEntityManager em;

    @Test
    void findByUserAndCourse() {
        User u = em.persist(new User());
        Course c = em.persist(new Course());
        Progress p = new Progress(); p.setUser(u); p.setCourse(c);
        em.persist(p);

        assertThat(repo.findByUserAndCourse(u, c)).isEqualTo(p);
    }
}
