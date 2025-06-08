package com.example.demo.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseE2ETest {

    protected WebDriver        driver;
    protected WebDriverWait    wait;
    protected final String BASE_URL = "http://localhost:3000";

    @BeforeAll
    void setUpDriver() {
        WebDriverManager.chromedriver().setup();   // otomatik driver indirme
        driver = new ChromeDriver();
        wait   = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterAll
    void tearDown() {
        driver.quit();
    }

    /** Yardımcı metot: butona tıkla ve sonrasında bekle */
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void type(By locator, String text) {
        WebElement el = find(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected void loginIfNeeded() {
        // Navbar’da "Profile" linki görünüyorsa zaten giriş yapılmıştır
        boolean loggedIn = !driver.findElements(
                By.cssSelector("nav a[href='/profile']")).isEmpty();

        if (loggedIn) return;

        driver.get(BASE_URL + "/login");

        /* Form elemanlarını doldur */
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[type='email']"))).sendKeys("emir@gmail.com");
        driver.findElement(By.cssSelector("input[type='password']"))
                .sendKeys("xePo9rLw#drL7U");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        /* Giriş sonrası /courses yönlendirmesi veya navbar'da isim */
        wait.until(ExpectedConditions.urlContains("/courses"));
    }

}
