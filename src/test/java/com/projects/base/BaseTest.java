package com.projects.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.codeborne.selenide.Selenide.*;

public abstract class BaseTest {

    @BeforeEach
    void setUp() {
        Configuration.baseUrl = "https://www.demoblaze.com";
        Configuration.headless = true;          // run in headless mode
        Configuration.browserSize = "1920x1080";
        Configuration.screenshots = true;       // allow screenshots
        Configuration.savePageSource = true;    // allow saving page source

        // Attach screenshots & page source automatically (on failure)
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));

        open("/");
        WebDriverRunner.getWebDriver().manage().window().maximize();

        // Optional: screenshot right after opening (for step-level tracking)
        attachScreenshot("Initial Page");
    }

    @AfterEach
    void tearDown() {
        // Take screenshot at the end of each test (even if it passed)
        attachScreenshot("Final Page State");
        closeWebDriver();
    }

    /**
     * Helper to attach screenshots to Allure on every step
     */
    protected void attachScreenshot(String name) {
        File screenshot = Screenshots.takeScreenShotAsFile();
        if (screenshot != null) {
            try {
                Allure.addAttachment(name, new FileInputStream(screenshot));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
