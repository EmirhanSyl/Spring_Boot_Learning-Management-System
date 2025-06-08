package com.example.demo.e2e;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

class CourseCatalogE2ETest extends BaseE2ETest {

    @Test
    @DisplayName("Kurs listesi sayfasında en az bir kurs kartı görüntülenir")
    void listCourses() {
        driver.get(BASE_URL + "/courses");

        int cardCount = driver.findElements(By.cssSelector(".course-card")).size();
        assertThat(cardCount).isGreaterThan(0);
    }
}
