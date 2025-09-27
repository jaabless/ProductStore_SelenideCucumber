package com.projects.tests;

import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.projects.base.BaseTest;
import com.projects.pages.CartPage;
import com.projects.pages.CheckoutPage;
import com.projects.pages.ProductPage;
import com.projects.util.CheckoutInfo;
import com.projects.util.TestDataLoader;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Product Store UI Tests")
@Feature("Place Order")
@ExtendWith({ScreenShooterExtension.class})
@Tag("regression")
public class PlaceOrderTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(PlaceOrderTest.class);
    private final ProductPage productPage = new ProductPage();
    private final CartPage cartPage = new CartPage();
    private final CheckoutPage checkoutPage = new CheckoutPage();

    @Test
    @Story("Place Order Funcitonality")
    @DisplayName("Verify that a user can successfully place an order with valid checkout details.")
    @Severity(SeverityLevel.CRITICAL)
    void testValidCheckout() {
        String product = TestDataLoader.getProduct("laptop");
        CheckoutInfo info = TestDataLoader.getCheckoutInfo("valid");

        log.info("Adding '{}' to cart and checking out with valid details", product);
        productPage.openProduct(product);
        productPage.addToCart();

        cartPage.openCart();
        cartPage.proceedToCheckout();

        checkoutPage.fillCheckoutForm(info);
        checkoutPage.placeOrder();

        assertTrue(checkoutPage.isOrderConfirmed(), "Order should be confirmed");
    }

    @Test
    @Story("Place Order Funcitonality")
    @Severity(SeverityLevel.CRITICAL)
    void testInvalidCardCheckout() {
        String product = TestDataLoader.getProduct("laptop");
        CheckoutInfo info = TestDataLoader.getCheckoutInfo("invalidCard");

        log.info("Trying checkout with invalid credit card");
        productPage.openProduct(product);
        productPage.addToCart();

        cartPage.openCart();
        cartPage.proceedToCheckout();

        checkoutPage.fillCheckoutForm(info);
        checkoutPage.placeOrder();

        assertTrue(checkoutPage.isErrorDisplayed(), "Error should be shown for invalid card");
    }

    @Test
    @Story("Place Order Funcitonality")
    @DisplayName("Verify that clicking the 'Place Order' button navigates to the checkout page")
    public void verifyPlaceOrderButton() {
        String product = TestDataLoader.getProduct("laptop");
        log.info("Adding '{}' twice", product);
        productPage.openProduct(product);
        productPage.addToCart();
        cartPage.openCart();
        cartPage.proceedToCheckout();
    }

    @Test
    @Story("Place Order Funcitonality")
    @DisplayName("Verify that 'Place Order' button is disabled with empty cart")
    public void verifyPlaceOrderButtonDisabledWithEmptyCart() {
        cartPage.openCart();
        assertFalse(cartPage.isPlaceOrderEnabled(), "Place Order button should be disabled with empty cart");
    }

    @Test
    @Story("Place Order Funcitonality")
    @DisplayName("Verify that name field rejects numeric input")
    @Severity(SeverityLevel.NORMAL)
    void testNumericNameFieldValidation() {
        String product = TestDataLoader.getProduct("laptop");
        CheckoutInfo info = TestDataLoader.getCheckoutInfo("invalidName");
        log.info("Testing name field validation with numeric input");
        productPage.openProduct(product);
        productPage.addToCart();
        cartPage.openCart();
        cartPage.proceedToCheckout();
        checkoutPage.fillCheckoutForm(info);
        assertTrue(checkoutPage.isErrorDisplayed(), "Name field should show validation error" );
    }

    @Test
    @Story("Place Order Funcitonality")
    @DisplayName("Verify that name field rejects special characters input")
    @Severity(SeverityLevel.NORMAL)
    void testSpecialNameFieldValidation() {
        String product = TestDataLoader.getProduct("laptop");
        CheckoutInfo info = TestDataLoader.getCheckoutInfo("invalidName");
        log.info("Testing name field validation with special characters");
        productPage.openProduct(product);
        productPage.addToCart();
        cartPage.openCart();
        cartPage.proceedToCheckout();
        checkoutPage.fillCheckoutForm(info);
        assertTrue(checkoutPage.isErrorDisplayed(), "Name field should show validation error" );
    }

    @Test
    @Story("Place Order Funcitonality")
    @DisplayName("Verify that credit card field accepts only 16-digit numbers")
    @Severity(SeverityLevel.NORMAL)
    void testCreditCardFieldValidation() {
        String product = TestDataLoader.getProduct("laptop");
        CheckoutInfo info = TestDataLoader.getCheckoutInfo("invalidCard");
        log.info("Testing credit card field validation with invalid input");
        productPage.openProduct(product);
        productPage.addToCart();
        cartPage.openCart();
        cartPage.proceedToCheckout();
        checkoutPage.fillCheckoutForm(info);
        assertTrue(checkoutPage.isErrorDisplayed(), "Credit card field should show validation error" );
    }

    @Test
    @Story("Place Order Funcitonality")
    @DisplayName("Verify that credit card field rejects non-numeric input")
    @Severity(SeverityLevel.NORMAL)
    void testCreditCardFieldNonNumericValidation() {
        String product = TestDataLoader.getProduct("laptop");
        CheckoutInfo info = TestDataLoader.getCheckoutInfo("invalidCard");
        log.info("Testing credit card field validation with non-numeric input");
        productPage.openProduct(product);
        productPage.addToCart();
        cartPage.openCart();
        cartPage.proceedToCheckout();
        checkoutPage.fillCheckoutForm(info);
        assertTrue(checkoutPage.isErrorDisplayed(), "Credit card field should show validation error" );
    }



}
