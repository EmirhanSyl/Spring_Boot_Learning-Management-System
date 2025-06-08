package com.example.demo.e2e;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginE2ETest extends BaseE2ETest {

    @Test
    @DisplayName("Geçerli kimlik bilgileri ile giriş başarılı")
    void loginSuccess() {
        driver.get(BASE_URL + "/login");

        // Spinner varsa kalksın
        wait.until(ExpectedConditions
                .invisibilityOfElementLocated(By.cssSelector(".page-loader")));

        // E-posta alanı – id yerine type’e göre seçiyoruz
        WebElement emailInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input[type='email']")));
        emailInput.sendKeys("ali@example.com");

        WebElement pwdInput = driver.findElement(
                By.cssSelector("input[type='password']"));
        pwdInput.sendKeys("pass123");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Başarılı giriş sinyali
        wait.until(ExpectedConditions.urlContains("/courses"));

        /* 3) Kurs listesi kartlarının gerçekten yüklendiğini doğrula */
        int cardCount = driver.findElements(By.cssSelector(".course-card")).size();
        assertThat(cardCount).isGreaterThan(0);
    }
}
