package com.projects.selenideTests;

import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.projects.base.BaseTest;
import com.projects.pages.ContactPage;
import com.projects.util.ContactInfo;
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
@Feature("Contact Form")
@ExtendWith({ScreenShooterExtension.class})
@Tag("regression")
public class ContactTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(ContactTest.class);
    private final ContactPage contactPage = new ContactPage();

    @Test
    @Story("Contact Functionality")
    @DisplayName("Verify that a user can successfully submit the contact form with valid details.")
    @Severity(SeverityLevel.CRITICAL)
    void testValidContactSubmission() {
        ContactInfo info = TestDataLoader.getContactInfo("valid");

        log.info("Submitting valid contact form");
        contactPage.openContactForm();
        contactPage.fillContactForm(info);
        contactPage.submitContactForm();

        assertTrue(contactPage.shouldShowConfirmation(), "Success alert should appear after valid submission");
    }

    @Test
    @Story("Contact Functionality")
    @DisplayName("Verify that submitting the contact form with an invalid email shows an error.")
    @Severity(SeverityLevel.NORMAL)
    void testInvalidEmailContactSubmission() {
        ContactInfo info = TestDataLoader.getContactInfo("invalid");

        log.info("Submitting contact form with invalid email");
        contactPage.openContactForm();
        contactPage.fillContactForm(info);
        contactPage.submitContactForm();

        assertTrue(contactPage.shouldShowEmailValidationError(), "Error alert should appear for invalid email");
    }
}