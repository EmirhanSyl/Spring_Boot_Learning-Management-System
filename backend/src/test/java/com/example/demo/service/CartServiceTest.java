package com.example.demo.service;

import com.example.demo.dto.CartRequest;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock  CartRepository cartRepo;
    @Mock  UserRepository userRepo;
    @Mock  CourseRepository courseRepo;
    @InjectMocks CartService service;

    @Test
    @DisplayName("addToCart → kullanıcı & kurs varsa kaydeder")
    void addToCart_success() {
        User u = new User();   u.setId(1L);
        Course c = new Course(); c.setId(101L);

        when(userRepo.findById(1L)).thenReturn(Optional.of(u));
        when(courseRepo.findById(101L)).thenReturn(Optional.of(c));
        when(cartRepo.save(any(Cart.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        CartRequest req = new CartRequest();
        req.setUserId(1L); req.setCourseId(101L);

        Cart saved = service.addToCart(req);

        assertThat(saved).isNotNull()
                .extracting(Cart::getUser, Cart::getCourse)
                .containsExactly(u, c);
    }

    @Test
    @DisplayName("addToCart → kullanıcı bulunamazsa null döner")
    void addToCart_missingUser() {
        CartRequest req = new CartRequest();
        req.setUserId(99L); req.setCourseId(101L);

        assertThat(service.addToCart(req)).isNull();
        verify(cartRepo, never()).save(any());
    }

    @Test
    void removeFromCart_delegatesToRepository() {
        service.removeFromCart(555L);
        verify(cartRepo).deleteById(555L);
    }
}
