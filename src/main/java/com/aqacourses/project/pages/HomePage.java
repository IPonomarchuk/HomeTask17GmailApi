package com.aqacourses.project.pages;

import com.aqacourses.project.base.BaseTest;
import com.aqacourses.project.gmail.CreateNewGmailEmailUsingDots;
import com.aqacourses.project.gmail.GetEmailsFromGmail;
import com.aqacourses.project.utils.YamlParser;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;

public class HomePage extends AbstractPage {

    private final String CONFIRMATION_MESSAGE = "You need to sign in or sign up before continuing.";

    // Web Elements
    @FindBy(xpath = "//a[.='Register as a tester']")
    private WebElement registerLink;

    @FindBy(xpath = "//input[@class='string optional form-control']")
    private WebElement nameInput;

    @FindBy(xpath = "//input[@class='string email optional form-control']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@class='password optional form-control']")
    private WebElement passwordInput;

    @FindBy(xpath = "//div[@class='checkbox']//span[@class='c-indicator']")
    private WebElement termsAndConditionsCheckbox;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@id='flashes']")
    private WebElement toasterMessage;

    /**
     * Constructor
     *
     * @param testClass
     */
    public HomePage(BaseTest testClass) {
        super(testClass);
    }

    /**
     * Click 'Register new User' link
     */
    public void clickRegisterNewUser() {
        testClass.waitTillElementIsVisible(registerLink);
        registerLink.click();
    }

    /**
     * Fill all inputs
     * Click 'Submit' button
     * Get confirmation URL from GMAIL inbox
     * Open this URL
     *
     * @throws IOException
     */
    public void registerNewUser() throws IOException {
        testClass.waitTillElementIsVisible(nameInput);
        nameInput.sendKeys(YamlParser.getYamlData().getName());
        emailInput.sendKeys(CreateNewGmailEmailUsingDots.newEmail());
        passwordInput.sendKeys(YamlParser.getYamlData().getPassword());
        termsAndConditionsCheckbox.click();
        submitButton.click();
        try {
            testClass.getDriver().get(getConfirmationUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get confirmation URL from GMAIL inbox
     *
     * @return String that represents confirmation URL
     * @throws IOException
     */
    private String getConfirmationUrl() throws IOException {
        return GetEmailsFromGmail.getInviteUrl();
    }

    /**
     * Verify registration comparing confirmation messages
     */
    public void verifyRegistration() {
        testClass.waitTillElementIsVisible(toasterMessage);
        Assert.assertEquals("Message are not the same", CONFIRMATION_MESSAGE, toasterMessage.getText());
    }


}
