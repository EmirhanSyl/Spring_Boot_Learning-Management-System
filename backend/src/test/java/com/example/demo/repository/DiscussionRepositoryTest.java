package com.example.demo.repository;

import com.example.demo.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DiscussionRepositoryTest {

    @Autowired DiscussionRepository repo;
    @Autowired TestEntityManager em;

    @Test
    void findByCourse() {
        Course c = em.persist(new Course());
        Discussion d = new Discussion(); d.setCourse(c); d.setContent("Merhaba");
        em.persist(d);

        assertThat(repo.findByCourse(c)).containsExactly(d);
    }
}
