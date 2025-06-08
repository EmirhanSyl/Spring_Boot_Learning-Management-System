package com.example.demo.e2e;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.assertj.core.api.Assertions.assertThat;

class EnrollCourseE2ETest extends BaseE2ETest {

    /* Seçiciler */
    private static final By COURSE_CARD   = By.cssSelector(".course-card");
    private static final By ENROLL_BTN    = By.cssSelector(".course-card .enroll-button");
    private static final By ENROLLED_ROW  = By.cssSelector("table.performance-table tbody tr td");
    private static final By PROFILE_LINK  = By.cssSelector("nav a[href='/profile']");

    @Test
    @DisplayName("Kurs Enroll → Profile/Courses Enrolled listesine eklenir")
    void enrollAddsCourseToProfile() {

        /* 1) Gerekirse oturum aç */
        loginIfNeeded();

        /* 2) /courses sayfası */
        driver.get(BASE_URL + "/courses");

        /* 3) İlk kurs kartı + başlığını al */
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(COURSE_CARD, 0));
        WebElement firstCard = driver.findElements(COURSE_CARD).get(0);
        String courseTitle = firstCard.findElement(By.cssSelector(".course-heading")).getText();

        /* 4) Enroll */
        firstCard.findElement(ENROLL_BTN).click();

        /* 5) Toast veya başka sinyal yerine doğrudan Profile’a git */
        click(PROFILE_LINK);
        wait.until(ExpectedConditions.urlContains("/profile"));

        /* 6) Courses Enrolled tablosunda kurs başlığını ara */
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(ENROLLED_ROW, 0));
        boolean found = driver.findElements(ENROLLED_ROW)
                .stream()
                .anyMatch(td -> td.getText().equalsIgnoreCase(courseTitle));

        assertThat(found)
                .as("Enrolled course should appear in profile list")
                .isTrue();
    }
}
