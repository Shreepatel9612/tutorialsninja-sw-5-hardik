package com.tutorialsninja.testsuite;

import com.tutorialsninja.pages.HomePage;
import com.tutorialsninja.pages.LoginPage;
import com.tutorialsninja.pages.MyAccountPage;
import com.tutorialsninja.pages.RegisterAccountPage;
import com.tutorialsninja.testbase.BaseTest;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class MyAccountsTest extends BaseTest {

    HomePage homePage = new HomePage();
    RegisterAccountPage register = new RegisterAccountPage();
    LoginPage loginPage = new LoginPage();
    MyAccountPage myAccountPage = new MyAccountPage();


    @BeforeMethod
    public void inIt() {
        homePage = new HomePage();
        register = new RegisterAccountPage();
        loginPage = new LoginPage();
        myAccountPage = new MyAccountPage();

    }

    @Test(groups = {"sanity"})
    public void selectMyAccountOptions(String option) {
        List<WebElement> myAccountList = homePage.getListOfMyAccountOption();
        try {
            for (WebElement options : myAccountList) {
                if (options.getText().equalsIgnoreCase(option)) {
                    options.click();
                }
            }
        } catch (StaleElementReferenceException e) {
            myAccountList = homePage.getListOfMyAccountOption();
        }
    }

    @Test(groups = {"smoke"})
    public void verifyUserShouldNavigateToRegisterPageSuccessfully() {
        homePage.clickOnMyAccount();
        selectMyAccountOptions("Register");
        Assert.assertEquals(register.getRegisterAccountTitle(), "Register Account",
                "Register page not displayed");
    }

    @Test(groups = {"regression"})
    public void verifyUserShouldNavigateToLoginPageSuccessfully() {
        homePage.clickOnMyAccount();
        selectMyAccountOptions("Login");
        Assert.assertEquals(loginPage.getLoginPageTitle(), "Returning Customer",
                "Login page not displayed");
    }

    @Test(groups = {"regression"})
    public void verifyThatUserRegisterAccountSuccessfully() {

        SoftAssert softAssert = new SoftAssert();

        homePage.clickOnMyAccount();
        selectMyAccountOptions("Register");

        register.enterFirstName("hardik");
        register.enterLastName("pokiya");
        register.enterEmail("shree9612@gmail.com");
        register.enterPhoneNumber("07436879725");
        register.enterPassword("Shree@9909");
        register.enterConfirmPassword("Shree@9909");
        register.selectSubscribe("Yes");
        register.clickOnPrivacyPolicyCheckBox();
        register.clickOnContinueButton();
        softAssert.assertEquals(register.getAccountRegistrationConformationMessage(),
                "Your Account Has Been Created!", "Your Account Not Created!");
        register.clickOnContinueWithConfirmation();

        homePage.clickOnMyAccount();
        selectMyAccountOptions("Logout");
        softAssert.assertEquals(homePage.getConfirmationMessageOfLogout(),
                "Account Logout", "Not logged out");
        homePage.clickOnContinueButton();
        softAssert.assertAll();
    }

    @Test(groups = {"regression"})
    public void verifyThatUserShouldLoginAndLogoutSuccessfully() {
        SoftAssert softAssert = new SoftAssert();

        homePage.clickOnMyAccount();
        selectMyAccountOptions("Login");
        loginPage.enterEmail("shree9612@gmail.com");
        loginPage.enterPassword("Shree@9909");
        loginPage.clickOnLogInButton();

        softAssert.assertEquals(myAccountPage.getMyAccountPageTitle(), "My Account",
                "My Account Is not Matched!");
        homePage.clickOnMyAccount();
        selectMyAccountOptions("Logout");

        softAssert.assertEquals(homePage.getConfirmationMessageOfLogout(),
                "Account Logout", "Not logged out");
        homePage.clickOnContinueButton();
        softAssert.assertAll();
    }
}
