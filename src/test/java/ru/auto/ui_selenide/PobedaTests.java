package ru.auto.ui_selenide;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.auto.ui_selenide.POM.PobedaFindOrderPage;
import ru.auto.ui_selenide.POM.PobedaMainPage;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class PobedaTests {

    PobedaMainPage objMainPage;
    PobedaFindOrderPage objFindOrderPage;

    @BeforeEach
    public void setUp() {
        Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, StepsUI.setOptionsDriverSELENIDE());

        objMainPage = new PobedaMainPage();
        objMainPage.checkTitlePage("Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками");
        objMainPage.checkLogo();
    }

    @Test
    @DisplayName("Проверка всплывающего меню 'Информация'")
    public void checkPopUpInformation() {
        objMainPage.moveСursorToModuleInfo();
        objMainPage.checkVisionTitlesOfInfo();
    }

    @Test
    @DisplayName("Проверка поиска Авиабилета без даты вылета")
    public void checkFindTicketWithoutDateFrom() {
        objMainPage.scrollPageToModuleFind();
        objMainPage.checkInputsModuleFind();
        objMainPage.setFromCity("Москва");
        objMainPage.setToCity("Санкт-Петербург");
        objMainPage.clickFind();
        Assertions.assertTrue(objMainPage.checkFailsDateFrom());
    }

    @Test
    @DisplayName("Проверка бронирования")
    public void searchBooking() {
        objMainPage.clickBookingManager();
        objMainPage.checkInputsModuleFindBooking();
        objMainPage.setInputSurname("Qwerty");
        objMainPage.setInputNumberBooking("XXXXX1");
        objMainPage.clickFind();

        StepsUI.changeTab(getWebDriver());

        objFindOrderPage = new PobedaFindOrderPage();
        objFindOrderPage.CheckUrlPage();
        objFindOrderPage.acceptCheckboxConfirm();
        objFindOrderPage.clickButtonSearch();
        objFindOrderPage.getErrorText();
    }
}
