package com.example.demo.controller;

import com.example.demo.dto.CartRequest;
import com.example.demo.entity.Cart;
import com.example.demo.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static Stream<CartRequest> requestProvider() {
        CartRequest req = new CartRequest();
        req.setCourseId(1L);
        req.setUserId(1L);
        return Stream.of(req);
    }

    @ParameterizedTest
    @MethodSource("requestProvider")
    void addToCart(CartRequest request) throws Exception {
        Cart cart = new Cart();
        given(cartService.addToCart(any(CartRequest.class))).willReturn(cart);

        mockMvc.perform(post("/api/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource("1")
    void removeFromCart(Long id) throws Exception {
        mockMvc.perform(delete("/api/carts/{id}", id))
                .andExpect(status().isOk());
    }
}
