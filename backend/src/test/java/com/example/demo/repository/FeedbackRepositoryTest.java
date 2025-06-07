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
class FeedbackRepositoryTest {

    @Autowired FeedbackRepository repo;
    @Autowired TestEntityManager em;

    @Test
    void persistFeedback() {
        Course c = em.persist(new Course());
        Feedback f = new Feedback(); f.setCourse(c); f.setComment("👍");
        repo.save(f);

        assertThat(repo.findAll()).singleElement()
                .extracting(Feedback::getComment)
                .isEqualTo("👍");
    }
}
