package com.projects.selenideTests;

import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.projects.base.BaseTest;
import com.projects.pages.CartPage;
import com.projects.pages.ProductPage;
import com.projects.util.TestDataLoader;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.codeborne.selenide.Selenide.sleep;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Product Store UI Tests")
@Feature("Cart")
@ExtendWith({ScreenShooterExtension.class})
@Tag("regression")
public class CartTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(CartTest.class);
    private final ProductPage productPage = new ProductPage();
    private final CartPage cartPage = new CartPage();

    @Test
    @Story("Cart Functionality")
    @DisplayName("Verify clicking the \"Add to Cart\" button on a product page adds the product to the cart.")
    @Severity(SeverityLevel.CRITICAL)
    void testAddSingleItem() {
        String product = TestDataLoader.getProduct("laptop");

        log.info("Adding '{}' to cart", product);
        productPage.openProduct(product);
        productPage.addToCart();

        cartPage.openCart();
        cartPage.waitUntilProductVisible(product);
        assertTrue(cartPage.containsProduct(product), "Cart should contain the added product");
    }

    @Test
    @Story("Cart Functionality")
    @DisplayName("Verify  that adding the same product multiple times increases quantity")
    @Severity(SeverityLevel.NORMAL)
    void testAddSameItemTwice() {
        String product = TestDataLoader.getProduct("laptop");

        log.info("Adding '{}' twice", product);
        productPage.openProduct(product);
        productPage.addToCart();
        productPage.addToCart();

        cartPage.openCart();
        int actualCount = cartPage.getEntriesFor(product);

        assertEquals(2, actualCount,
                "Quantity should be 2 when the same product is added twice");
    }

    @Test
    @Story("Cart Functionality")
    @DisplayName("Verify immediate feedback after adding a product to the cart")
    public void verifyAddingProductToCart() {
        String product = TestDataLoader.getProduct("laptop");
        log.info("Adding '{}'", product);
        productPage.openProduct(product);
        productPage.addToCart();
        productPage.verifyImmediateFeedback();
    }

    @Test
    @Story("Cart Functionality")
    @DisplayName("Verify removing an item not in the cart does not affect cart state")
    @Severity(SeverityLevel.MINOR)
    void testRemoveNonexistentItem() {
        String product = TestDataLoader.getProduct("phone");

        log.info("Trying to remove product '{}' not in cart", product);
        cartPage.openCart();
        cartPage.removeItem(product);

        assertFalse(cartPage.containsProduct(product), "Nonexistent product should not appear in cart");
    }

    @Test
    @Story("Cart Functionality")
    @DisplayName("Verify a product can be removed from the cart and updates the cart state accordingly")
    @Severity(SeverityLevel.CRITICAL)
    void testRemoveItem() {
        String product = TestDataLoader.getProduct("phone");

        log.info("Adding and then removing product '{}'", product);
        productPage.openProduct(product);
        productPage.addToCart();

        cartPage.openCart();
        sleep(5000);
        cartPage.removeItem(product);

        cartPage.shouldNotContainProduct(product);
    }

    @Test
    @Story("Cart Functionality")
    @DisplayName("Verify cart contents persist after page reload")
    @Severity(SeverityLevel.NORMAL)
    void testCartPersistenceAfterReload() {
        String product = TestDataLoader.getProduct("phone");

        log.info("Adding '{}' and refreshing cart", product);
        productPage.openProduct(product);
        productPage.addToCart();
        cartPage.openCart();

        assertTrue(cartPage.containsProduct(product), "Cart should contain item before reload");
        cartPage.refresh();
        assertTrue(cartPage.containsProduct(product), "Cart should still contain item after reload");
    }

    @Test
    @Story("Cart Functionality")
    @DisplayName("Verify that clicking the 'Place Order' button navigates to the checkout page")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyPlaceOrderButton() {
        String product = TestDataLoader.getProduct("phone");

        log.info("Adding product '{}'", product);
        productPage.openProduct(product);
        productPage.addToCart();
        cartPage.openCart();
        assertTrue(cartPage.isPlaceOrderEnabled(), "Place Order button should be enabled");
    }

}
