package com.example.demo.repository;

import com.example.demo.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CartRepositoryTest {

    @Autowired CartRepository repo;
    @Autowired TestEntityManager em;

    @Test
    void saveAndFind() {
        User u = em.persist(new User());
        Course c = em.persist(new Course());
        Cart cart = new Cart(); cart.setUser(u); cart.setCourse(c);
        repo.save(cart);

        assertThat(repo.findAll()).hasSize(1).first().isEqualTo(cart);
    }
}
