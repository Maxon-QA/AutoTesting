package ru.auto.ui_selenide.POM;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class PobedaMainPage {

    String BASE_URL = "https://www.flypobeda.ru/";

    private ElementsCollection logo = $$("header div img, header div canvas");

    private SelenideElement moduleInfo = $("header a[href*='info']");
    private SelenideElement titleOfInfoPrepareToFlight = $(By.xpath("//header//div//a[text()='Подготовка к полёту']"));
    private SelenideElement titleOfInfoUsefulData = $(By.xpath("//header//div//a[text()='Полезная информация']"));
    private SelenideElement titleOfInfoAboutCompany = $(By.xpath("//header//div//a[text()='О компании']"));

    final String locatorModuleFind = "//div[contains(@class,'root-card')]//form";
    private SelenideElement ModuleFind = $(By.xpath(locatorModuleFind));
    private SelenideElement inputsFromModuleFind = $(By.xpath(locatorModuleFind + "//input[@placeholder='Откуда']"));
    private SelenideElement inputsToModuleFind = $(By.xpath(locatorModuleFind + "//input[@placeholder='Куда']"));
    private SelenideElement inputsDateFromModuleFind = $(By.xpath(locatorModuleFind + "//input[@placeholder='Туда']"));
    private SelenideElement inputsDateToModuleFind = $(By.xpath(locatorModuleFind + "//input[@placeholder='Обратно']"));
    private SelenideElement buttonFind = $(By.xpath(locatorModuleFind + "//button[@type='submit']"));

    private SelenideElement buttonBookingManager = $(By.xpath("//div[contains(@class,'root-tabsControlList')]//span[text()='Управление бронированием']/../.."));
    private SelenideElement inputSurname = $(By.xpath(locatorModuleFind + "//input[@placeholder='Фамилия клиента']"));
    private SelenideElement inputNumberBooking = $(By.xpath(locatorModuleFind + "//input[@placeholder='Номер бронирования или билета']"));


    public PobedaMainPage() {
        open(BASE_URL);
    }

    public void checkTitlePage(String title) {
        webdriver().shouldHave(WebDriverConditions.title(title));
    }

    public void checkLogo() {
        logo.filter(visible).shouldHave(sizeGreaterThanOrEqual(1));
    }

    public void moveСursorToModuleInfo() {
        moduleInfo.hover();
    }

    public void checkVisionTitlesOfInfo() {
        titleOfInfoPrepareToFlight.shouldBe(visible);
        titleOfInfoUsefulData.shouldBe(visible);
        titleOfInfoAboutCompany.shouldBe(visible);
    }

    public void scrollPageToModuleFind() {
        ModuleFind.scrollIntoCenter();
    }

    public void checkInputsModuleFind() {
        inputsFromModuleFind.shouldBe(visible);
        inputsToModuleFind.shouldBe(visible);
        inputsDateFromModuleFind.shouldBe(visible);
        inputsDateToModuleFind.shouldBe(visible);
    }


    public void setFromCity(String cityName) {
        inputsFromModuleFind.click();
        inputsFromModuleFind.press(Keys.BACK_SPACE);
        inputsFromModuleFind.setValue(cityName);
        inputsFromModuleFind.press(Keys.DOWN);
        inputsFromModuleFind.pressEnter();
    }

    public void setToCity(String cityName) {
        inputsToModuleFind.click();
        inputsToModuleFind.press(Keys.BACK_SPACE);
        inputsToModuleFind.setValue(cityName);
        inputsToModuleFind.setValue(cityName);
        inputsToModuleFind.pressEnter();
    }

    public void clickFind() {
        buttonFind.click();
    }

    public boolean checkFailsDateFrom() {
        String isFail = inputsDateFromModuleFind.parent().getAttribute("data-failed");

        if (isFail == null) {
            return false;
        } else return Boolean.parseBoolean(isFail);
    }

    public void clickBookingManager() {
        buttonBookingManager.click();
    }

    public void checkInputsModuleFindBooking() {
        inputSurname.shouldBe(clickable);
        inputSurname.shouldBe(visible);
        inputNumberBooking.shouldBe(visible);
        buttonFind.shouldBe(visible);
    }

    public void setInputSurname(String surname) {
        inputSurname.setValue(surname);
    }

    public void setInputNumberBooking(String number) {
        inputNumberBooking.setValue(number);
    }
}

