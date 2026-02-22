package ru.auto.ui;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.v144.page.Page;

import java.util.Arrays;

public class StepsUI {

    public static ChromeOptions setOptionsDriver() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--disable-blink-features=AutomationControlled");
        return options;
    }

    public static void waitForVisibilityIfElement(WebElement webElement) {
        long waitingTime = 0;
        long MAX_WAITING_TIME = 90_000;
        long startLoadingTime = System.currentTimeMillis();

        while (!webElement.isDisplayed()) {
            if (waitingTime <= MAX_WAITING_TIME) {
                waitingTime = System.currentTimeMillis() - startLoadingTime;
            } else {
                throw new TimeoutException("Превышено время ожидания элемента  " + waitingTime + webElement);
            }
        }
    }
}
