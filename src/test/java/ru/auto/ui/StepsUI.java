package ru.auto.ui;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

public class StepsUI {

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
