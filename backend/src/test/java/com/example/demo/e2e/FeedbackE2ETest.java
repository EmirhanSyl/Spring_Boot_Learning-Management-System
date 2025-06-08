package com.example.demo.e2e;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

class FeedbackE2ETest extends BaseE2ETest {

    @Test
    @DisplayName("Kurs sayfasında geri bildirim gönderilir ve listede görünür")
    void submitFeedback() {
        driver.get(BASE_URL + "/course/101");  // örnek kurs ID

        type(By.id("feedback-input"), "İçerik çok açıklayıcı 👏");
        click(By.id("submit-feedback"));

        String lastComment = find(By.cssSelector(".feedback-list .item:last-child .comment")).getText();
        assertThat(lastComment).contains("İçerik çok açıklayıcı");
    }
}
