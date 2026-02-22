package ru.auto.ui;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;

public class PobedaTests {

    private final SoftAssertions softAssertions = new SoftAssertions();
    String BASE_URL = "https://www.google.com/";
    public WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--disable-blink-features=AutomationControlled");
        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(35));

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        softAssertions.assertAll();
        driver.close();
    }

    @Test
    @DisplayName("Проверка загрузки картинки поездки в калининград и смена языка")
    public void pobedaCheckSomething() {
        driver.get(BASE_URL);

        WebElement fieldFind = driver.findElement(By.cssSelector("textarea[title=\"Поиск\"]"));
        fieldFind.sendKeys("Сайт компании Победа");
        fieldFind.sendKeys(Keys.ENTER);

        WebElement firstLink = driver.findElement(By.cssSelector("#search h3"));
        firstLink.click();

        StepsUI.waitForVisibilityIfElement(
                driver.findElement(By.xpath("//*[text()='Полетели в Калининград!']")));

        WebElement imageToCity = driver.findElement(By.xpath(
                "//button/img[contains(@srcset, \"KALINIGRAD\")]"));
        softAssertions.assertThat(imageToCity.isDisplayed())
                .isTrue()
                .as("Картинка города не загрузилась");

        WebElement changeLanguage = driver.findElement(
                By.cssSelector("div.dp-1odq4wk-root-root button.dp-11x0mgu-root-root"));
        changeLanguage.click();

        WebElement iconEng = driver.findElement(
                By.xpath("//div[@data-popper-placement=\"bottom\"]//*[contains(text(), \"Eng\")]"));
        iconEng.click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//*"), "Ticket search"));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//*"), "Online check-in"));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//*"), "Manage my booking"));
    }
}
