package ru.auto.ui_selenide;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public class StepsUI {

    public static ChromeOptions setOptionsDriverSELENIDE() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        // Также часто добавляют для скрытия предупреждения о профиле
        options.addArguments("--disable-infobars");
        // Отключает флаг автоматизации
        options.addArguments("--disable-blink-features=AutomationControlled");
        // Отключает использование расширения автоматизации
        options.setExperimentalOption("useAutomationExtension", false);
        Configuration.browserSize = "1920x1080";
        return options;
    }

    public static void changeTab(WebDriver driver) {
        String currentTab = driver.getWindowHandle();
        Set<String> allTabs = driver.getWindowHandles();
        for (String tab : allTabs) {
            if (!tab.equals(currentTab)) {
                driver.switchTo().window(tab);
                break;
            }
        }
    }
}
