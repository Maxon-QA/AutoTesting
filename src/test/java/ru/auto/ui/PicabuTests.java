package ru.auto.ui;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;

public class PicabuTests {

    private final SoftAssertions softAssertions = new SoftAssertions();
    String PIKABU_URL = "https://pikabu.ru/";
    public WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--disable-blink-features=AutomationControlled");
        driver = new ChromeDriver(options);

        driver.get(PIKABU_URL);
    }

    @AfterEach
    public void tearDown() {
        softAssertions.assertAll();
        driver.close();
    }

    @Test
    @DisplayName("Проверка неудачной атвторизации")
    public void checkAuthNegative() {
        softAssertions.assertThat(driver.getTitle())
                .isEqualTo("Горячее – самые интересные и обсуждаемые посты | Пикабу")
                .as("Не совпадает тайтл");

        WebElement buttonAuth = driver.findElement(By.cssSelector("button.header-right-menu__login-button"));
        buttonAuth.click();

        WebElement windowModalAuth = driver.findElement(By.cssSelector("div.auth-modal"));
        softAssertions.assertThat(windowModalAuth.isDisplayed())
                .isTrue()
                .as("Не отображается модальное окно авторизации");

        WebElement fieldLogin = driver.findElement(By.cssSelector("div.auth-modal input[autocomplete=\"username\"]"));
        softAssertions.assertThat(fieldLogin.isDisplayed())
                .isTrue()
                .as("Не отображается поле Логин в модальном окне авторизации");

        WebElement fieldPass = driver.findElement(By.cssSelector("div.auth-modal input[autocomplete=\"password\"]"));
        softAssertions.assertThat(fieldPass.isDisplayed())
                .isTrue()
                .as("Не отображается поле Пароль в модальном окне авторизации");

        WebElement buttonAuthModal = driver.findElement(By.cssSelector("div.auth-modal div.auth__field_firstbtn span"));
        softAssertions.assertThat(buttonAuthModal.isDisplayed())
                .isTrue()
                .as("Не отображается кнопка Войти в модальном окне авторизации");

        fieldLogin.sendKeys("Qwerty");
        fieldPass.sendKeys("Qwerty");
        buttonAuthModal.click();

        WebElement popupAuthError = driver.findElement(By.cssSelector("div.popup__content span.auth__error.auth__error_top"));
        softAssertions.assertThat(popupAuthError.getText())
                .isEqualTo("Ошибка. Вы ввели неверные данные авторизации")
                .as("Текст ошибки авторизации не совпадает");
    }
}
