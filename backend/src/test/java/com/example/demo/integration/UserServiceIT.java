package com.example.demo.integration;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserServiceIT {

    @Autowired
    UserService service;

    @Test
    void authenticateUser() {
        User u = new User(); u.setEmail("ali@mail.com"); u.setPassword("pass");
        service.createUser(u);

        assertThat(service.authenticateUser("ali@mail.com","pass"))
                .isNotNull()
                .extracting(User::getEmail).isEqualTo("ali@mail.com");
    }
}
