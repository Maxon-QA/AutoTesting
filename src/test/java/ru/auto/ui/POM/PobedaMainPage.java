package ru.auto.ui.POM;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class PobedaMainPage {

    String BASE_URL = "https://www.flypobeda.ru/";
    WebDriver driver;

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


    public PobedaMainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void openMainPage() {
        driver.get(BASE_URL);
    }

    public String getTitleText() {
        return titleOfPage.getAttribute("textContent");
    }

    public void moveСursorToModuleInfo() {
        Actions actions = new Actions(driver);
        actions.moveToElement(moduleInfo).perform();
    }
}

