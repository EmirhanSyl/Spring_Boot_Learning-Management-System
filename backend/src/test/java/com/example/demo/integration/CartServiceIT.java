package com.example.demo.integration;

import com.example.demo.dto.CartRequest;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.service.CartService;
import com.example.demo.service.CourseService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CartServiceIT {

    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;

    @Test
    void addAndRemoveCart() {
        User u = userService.createUser(new User());
        Course c = courseService.createCourse(new Course());

        CartRequest req = new CartRequest();
        req.setUserId(u.getId()); req.setCourseId(c.getId());

        Cart cart = cartService.addToCart(req);
        cartService.removeFromCart(cart.getId());

        assertThat(cartService.addToCart(req)).isNotNull(); // tekrar eklenebilir
    }
}
