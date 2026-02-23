package ru.auto.ui_selenide.POM;


import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.url;

public class PobedaFindOrderPage {

    String BASE_URL = "https://ticket.flypobeda.ru/";

    private SelenideElement checkboxConfirm = $(By.xpath("//label//input[@type='checkbox']/following-sibling::span"));
    private SelenideElement buttonSearch = $("form button.btn_search--order");
    private SelenideElement errorBlock = $("section div.message_error");

    private SelenideElement loader = $("div.process-bar");


    public PobedaFindOrderPage() {
        //open(BASE_URL);
    }

    public void CheckUrlPage() {
        url().startsWith(BASE_URL);
    }

    public void acceptCheckboxConfirm() {
        loader.shouldNot(exist, Duration.ofSeconds(90));

        //Тут может всплыть капча
        checkboxConfirm.shouldBe(clickable, Duration.ofSeconds(30));
        checkboxConfirm.click();
    }

    public void clickButtonSearch() {
        buttonSearch.click();
    }

    public void getErrorText() {
       errorBlock.shouldHave(text("Заказ с указанными параметрами не найден"));
    }
}
