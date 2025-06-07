package com.example.demo.repository;

import com.example.demo.entity.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class QuestionRepositoryTest {

    @Autowired QuestionRepository repo;
    @Autowired TestEntityManager em;

    @Test
    void findByCourse() {
        Course c = em.persist(new Course());
        Questions q = new Questions(); q.setCourse(c); q.setQuestion("Hook?");
        em.persist(q);

        assertThat(repo.findByCourse(c)).containsExactly(q);
    }
}

