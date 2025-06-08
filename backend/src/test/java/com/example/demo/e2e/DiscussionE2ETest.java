package com.example.demo.e2e;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

class DiscussionE2ETest extends BaseE2ETest {

    @Test
    @DisplayName("Tartışma kanalına mesaj eklenir ve anında listelenir")
    void addDiscussionMessage() {
        driver.get(BASE_URL + "/course/102/discussions");

        type(By.id("discussion-input"), "Dependency Injection konusunda öneri?");
        click(By.id("post-button"));

        String latest = find(By.cssSelector(".discussion-item:last-child .content")).getText();
        assertThat(latest).contains("Dependency Injection konusunda öneri");
    }
}