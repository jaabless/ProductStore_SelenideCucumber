package com.projects.base;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public abstract class BasePage {
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);


    public void openPage(String relativeUrl) {
        open(relativeUrl);
    }

    public String getPageTitle() {
        return title();
    }

    public void clickNavLink(String linkText) {
        $$(".nav-link")
                .findBy(Condition.text(linkText))
                .shouldBe(Condition.visible, Duration.ofSeconds(2))
                .click();
    }
    protected SelenideElement getElement(By locator) {
        return $(locator).shouldBe(com.codeborne.selenide.Condition.visible);
    }

}
