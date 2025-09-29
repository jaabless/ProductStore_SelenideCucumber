package com.projects.selenideTests;

import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.projects.base.BaseTest;
import com.projects.pages.HomePage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Product Store UI Tests")
@Feature("Homepage")
@ExtendWith({ScreenShooterExtension.class})
@Tag("smoke")
public class HomePageTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(HomePageTest.class);
    private final HomePage homepage = new HomePage();

    @Test
    @Story("Homepage Functionality")
    @DisplayName("Verify that the homepage loads successfully and displays a list of products without login.")
    @Severity(SeverityLevel.CRITICAL)
    void testHomepageLoadsWithProducts() {
        log.info("Opening homepage");
        homepage.open();

        assertTrue(homepage.isLoaded(), "Homepage should be loaded");
        assertTrue(homepage.hasProducts(), "Homepage should display products");
    }

    @Test
    @Story("Homepage Functionality")
    @DisplayName("Verify that navigation links (Home, Contact) are displayed on the homepage.")
    @Severity(SeverityLevel.NORMAL)
    void testNavigationLinks() {
        log.info("Opening homepage and clicking Contact link");
        homepage.open();
        homepage.clickContact();

        assertTrue(homepage.isContactModalVisible(), "Contact modal should be visible");

        log.info("Closing contact modal");
        homepage.closeContactModal();
        assertFalse(homepage.isContactModalVisible(), "Contact modal should be closed");

        log.info("Clicking Home link");
        homepage.clickHome();
        assertTrue(homepage.hasProducts(), "Products should be visible after clicking Home link");
    }

    @Test
    @Story("Homepage Functionality")
    @DisplayName("Verify that each product card displays an image, title, and price.")
    @Severity(SeverityLevel.MINOR)
    void testProductCardsDisplayInfo() {
        log.info("Opening homepage to verify product cards");
        homepage.open();
        assertTrue(homepage.verifyFirstProductHasNamePriceThumbnail(), "All product cards should display image, title, and price");
    }

    @Test
    @Story("Homepage Functionality")
    @DisplayName("Verify clicking product name redirects to detailed product page")
    @Severity(SeverityLevel.NORMAL)
    void testProductLinkNavigation() {
        String product = homepage.getFirstProductName();
        log.info("Clicking on product '{}' to navigate to its detail page", product);
        homepage.open();
        homepage.clickProduct(product);
        assertTrue(homepage.addToCartButtonVisible(), "Should navigate to product detail page with Add to Cart button visible");
    }





}
