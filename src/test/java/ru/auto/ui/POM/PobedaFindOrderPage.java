package ru.auto.ui.POM;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PobedaFindOrderPage {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    @FindBy(xpath = "//label//input[@type='checkbox']/following-sibling::span")
    WebElement checkboxConfirm;

    @FindBy(css = "form button.btn_search--order")
    WebElement buttonSearch;

    @FindBy(css = "section div.message_error")
    WebElement errorBlock;

    public PobedaFindOrderPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public String getUrlPage() {
        return driver.getCurrentUrl();
    }

    public void acceptCheckboxConfirm() {
        wait.until(ExpectedConditions.elementToBeClickable(checkboxConfirm));
        checkboxConfirm.click();
    }

    public void clickButtonSearch() {
        buttonSearch.click();
    }

    public String getErrorText() {
        return errorBlock.getText();
    }
}
