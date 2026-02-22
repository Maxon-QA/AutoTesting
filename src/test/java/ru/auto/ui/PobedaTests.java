package ru.auto.ui;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.auto.ui.POM.PobedaFindOrderPage;
import ru.auto.ui.POM.PobedaMainPage;

import java.time.Duration;

public class PobedaTests {

    private final SoftAssertions softAssertions = new SoftAssertions();
    String BASE_URL = "https://www.google.com/";

    WebDriver driver;
    WebDriverWait wait;

    PobedaMainPage objMainPage;
    PobedaFindOrderPage objFindOrderPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver(StepsUI.setOptionsDriver());

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(35));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        objFindOrderPage = new PobedaFindOrderPage(driver);

        objMainPage = new PobedaMainPage(driver);
        objMainPage.openMainPage();
        Assertions.assertEquals(
                "Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками"
                , objMainPage.getTitleText()
                , "Название заголовка страницы не совпадает");
        Assertions.assertTrue(
                objMainPage.getLogo().stream().anyMatch(WebElement::isDisplayed)
                , "Логотип не отобразился");
    }

    @AfterEach
    public void tearDown() {
        softAssertions.assertAll();
        driver.close();
    }

    @Test
    @DisplayName("Проверка всплывающего меню \"Информация\"")
    public void checkPopUpInformation() {
        objMainPage.moveСursorToModuleInfo();

        Assertions.assertTrue(objMainPage.getTitleOfInfoPrepareToFlight().isDisplayed()
                , "Заголовок Подготовка к полёту - не отобразился");
        Assertions.assertTrue(objMainPage.getTitleOfInfoUsefulData().isDisplayed()
                , "Заголовок Полезная информация - не отобразился");
        Assertions.assertTrue(objMainPage.getTitleOfInfoAboutCompany().isDisplayed()
                , "Заголовок О компании - не отобразился");
    }

    @Test
    @DisplayName("Проверка поиска Авиабилета без даты вылета")
    public void checkFindTicketWithoutDateFrom() throws InterruptedException {
        objMainPage.scrollPageToModuleFind();
        objMainPage.checkInputsModuleFind();
        objMainPage.setFromCity("Москва");
        objMainPage.setToCity("Санкт-Петербург");
        objMainPage.clickFind();
        Assertions.assertTrue(objMainPage.checkFailsDateFrom());
    }

    @Test
    @DisplayName("Проверка бронирования")
    public void searchBooking(){
        objMainPage.clickBookingManager();
        objMainPage.checkInputsModuleFindBooking();
        objMainPage.setInputSurname("Qwerty");
        objMainPage.setInputNumberBooking("XXXXX1");
        objMainPage.clickFind();

        StepsUI.changeTab(driver);

        Assertions.assertTrue(objFindOrderPage.getUrlPage().startsWith("https://ticket.flypobeda.ru/"));
        objFindOrderPage.acceptCheckboxConfirm();
        objFindOrderPage.clickButtonSearch();
        Assertions.assertEquals("Заказ с указанными параметрами не найден"
                , objFindOrderPage.getErrorText());
    }

    @Test
    @DisplayName("Проверка загрузки картинки поездки в калининград и смена яызка")
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
