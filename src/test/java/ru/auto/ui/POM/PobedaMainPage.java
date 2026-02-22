package ru.auto.ui.POM;

import lombok.Getter;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PobedaMainPage {

    String BASE_URL = "https://www.flypobeda.ru/";
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    final String locatorModuleFind = "//div[contains(@class,'root-card')]//form";

    @FindBy(css = "title")
    WebElement titleOfPage;

    @Getter
    @FindBy(css = "header div img, header div canvas")
    List<WebElement> logo;

    @FindBy(css = "header a[href*='info']")
    WebElement moduleInfo;

    @Getter
    @FindBy(xpath = "//header//div//a[text()=\"Подготовка к полёту\"]")
    WebElement titleOfInfoPrepareToFlight;

    @Getter
    @FindBy(xpath = "//header//div//a[text()=\"Полезная информация\"]")
    WebElement titleOfInfoUsefulData;

    @Getter
    @FindBy(xpath = "//header//div//a[text()=\"О компании\"]")
    WebElement titleOfInfoAboutCompany;

    @FindBy(xpath = locatorModuleFind)
    WebElement moduleFind;

    @FindBy(xpath = locatorModuleFind + "//input[@placeholder='Откуда']")
    WebElement inputsFromModuleFind;

    @FindBy(xpath = locatorModuleFind + "//input[@placeholder='Куда']")
    WebElement inputsToModuleFind;

    @FindBy(xpath = locatorModuleFind + "//div[@aria-label='scrollable content']//div[@role='menuitem']")
    WebElement dropDownInput;

    @Getter
    @FindBy(xpath = locatorModuleFind + "//input[@placeholder='Туда']")
    WebElement inputsDateFromModuleFind;

    @FindBy(xpath = locatorModuleFind + "//input[@placeholder='Обратно']")
    WebElement inputsDateToModuleFind;

    @FindBy(xpath = locatorModuleFind + "//button[@type='submit']")
    WebElement buttonFindTicket;

    public PobedaMainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public void openMainPage() {
        driver.get(BASE_URL);
    }

    public String getTitleText() {
        return titleOfPage.getAttribute("textContent");
    }


    public void moveСursorToModuleInfo() {
        actions.moveToElement(moduleInfo).perform();
    }

    public void scrollPageToModuleFind() {
        actions.scrollToElement(moduleFind).perform();
    }

    public void setFromCity(String cityName) throws InterruptedException {
        inputsFromModuleFind.click();
        inputsFromModuleFind.sendKeys(Keys.BACK_SPACE);
        inputsFromModuleFind.sendKeys(cityName);

        // С другими типами ожидания никак не получилось победить "Проскакивание" ввода
        Thread.sleep(1000);
//        wait.until(ExpectedConditions.visibilityOf(dropDownInput));
        inputsFromModuleFind.sendKeys(Keys.DOWN);
//        wait.until(ExpectedConditions.attributeToBe(dropDownInput, "data-highlighted", "true"));
        inputsFromModuleFind.sendKeys(Keys.ENTER);
    }

    public void setToCity(String cityName) {
        inputsToModuleFind.click();
        inputsToModuleFind.sendKeys(Keys.BACK_SPACE);
        inputsToModuleFind.sendKeys(cityName);
        inputsToModuleFind.sendKeys(Keys.DOWN);
        inputsToModuleFind.sendKeys(Keys.ENTER);
    }

    public void clickFindTicket() {
        buttonFindTicket.click();
    }

    public boolean checkFailsDateFrom() {
        String s = inputsDateFromModuleFind.findElement(
                By.xpath("..")).getAttribute("data-failed");

        if (s == null) {
            return false;
        } else return Boolean.parseBoolean(s);
    }

    public void checkInputsModuleFind() {
        final SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(inputsFromModuleFind.isDisplayed())
                .isTrue();
        softAssertions.assertThat(inputsToModuleFind.isDisplayed())
                .isTrue();
        softAssertions.assertThat(inputsDateFromModuleFind.isDisplayed())
                .isTrue();
        softAssertions.assertThat(inputsDateToModuleFind.isDisplayed())
                .isTrue();
        softAssertions.assertAll();
    }
}

