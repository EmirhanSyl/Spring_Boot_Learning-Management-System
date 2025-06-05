package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @ParameterizedTest
    @CsvSource({"1,test@example.com"})
    void getUserById(Long id, String email) throws Exception {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        given(userService.getUserById(id)).willReturn(user);

        mockMvc.perform(get("/api/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }

    @ParameterizedTest
    @CsvSource("0")
    void getAllUsers(int dummy) throws Exception {
        given(userService.getAllUsers()).willReturn(List.of());
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    static Stream<User> userProvider() {
        User u = new User();
        u.setUsername("name");
        u.setEmail("user@example.com");
        return Stream.of(u);
    }

    @ParameterizedTest
    @MethodSource("userProvider")
    void createUser(User user) throws Exception {
        given(userService.createUser(any(User.class))).willReturn(user);

        mockMvc.perform(post("/api/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource("1")
    void updateUser(Long id) throws Exception {
        User user = new User();
        given(userService.updateUser(eq(id), any(User.class))).willReturn(user);

        mockMvc.perform(put("/api/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource("test@example.com")
    void getUserByEmail(String email) throws Exception {
        User user = new User();
        user.setEmail(email);
        given(userService.getUserByEmail(email)).willReturn(user);

        mockMvc.perform(get("/api/users/details").param("email", email))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource("1")
    void deleteUser(Long id) throws Exception {
        mockMvc.perform(delete("/api/users/{id}", id))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource({"email@example.com,password"})
    void login(String email, String password) throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        given(userService.authenticateUser(email, password)).willReturn(user);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(Map.of("email", email, "password", password))))
                .andExpect(status().isOk());
    }
}
