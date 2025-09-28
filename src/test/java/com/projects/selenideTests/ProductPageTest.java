package com.projects.selenideTests;

import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.projects.pages.CartPage;
import com.projects.pages.ProductPage;
import com.projects.util.TestDataLoader;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Epic("Product Store UI Tests")
@Feature("Product")
@ExtendWith({ScreenShooterExtension.class})
@Tag("smoke")
public class ProductPageTest {
    private static final Logger log = LoggerFactory.getLogger(CartTest.class);
    private final ProductPage productPage = new ProductPage();
    private final CartPage cartPage = new CartPage();

    @Test
    @DisplayName("Verify immediate feedback after adding a product to the cart")
    public void verifyAddingProductToCart() {
        String product = TestDataLoader.getProduct("laptop");
        log.info("Adding '{}' twice", product);
        productPage.openProduct(product);
        productPage.addToCart();

    }


}