package org.im.bdd.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.hamcrest.Matchers;
import org.im.serenity_withpom.pages.HomePage;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;


public class LoginStepDefs {
    @Managed
    WebDriver driver;

    @Steps
    private HomePage homePage;



//    @Managed
//    private HomePage homePage;

    @Given("I am on the home page")
    public void iAmOnTheHomePage() {
        homePage.open();
    }

    @And("I have entered the username {string}")
    public void iHaveEnteredTheUsername(String userName) {

        homePage.enterUserName(userName);
    }

    @And("I have entered the password {string}")
    public void iHaveEnteredThePassword(String password) {
        homePage.enterPassword(password);
    }

//    @When("I click the login button")
//    public void iClickTheLoginButton() {
//        // Write code here that turns the phrase above into concrete actions
//        homePage.clickLoginButton();
//    }

    @Then("I should land on the inventory page")
    public void iShouldLandOnTheInventoryPage() {
        assertThat(homePage.getDriver().getCurrentUrl(), Matchers.endsWith("/inventory.html"));
    }

    @Then("I should see an error message that contains {string}")
    public void iShouldSeeAnErrorMessageThatContains(String expectedError) {
        assertThat(homePage.getErrorMessage(), Matchers.containsString(expectedError));
    }

    @When("I click the login button")
    public void iClickTheLoginButton() {
        homePage.clickLoginButton();
    }

}
//import io.cucumber.java.en.And;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import net.thucydides.core.annotations.Managed;
//import net.thucydides.core.annotations.Steps;
//import org.hamcrest.Matchers;
//import org.im.serenity_withpom.pages.HomePage;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//
//public class LoginStepDefs {
//
//    @Managed
//    private HomePage homePage;
//
//    @Given("I am on the home page")
//    public void iAmOnTheHomePage() {
//        //Given
//     homePage.open();
//    }
//
//    @And("I have entered the username {string}")
//    public void iHaveEnteredTheUsername(String arg0) {
//        // when
//       homePage.enterUserName("standard_user");
//    }
//
//    @And("I have entered the password {string}")
//    public void iHaveEnteredThePassword(String arg0) {
//        homePage.enterPassword("secret_sauce");
//    }
//
//    @When("I click the login button")
//    public void iClickTheLoginButton() {
//        homePage.clickLoginButton();
//    }
//
//    @Then("I should land on the inventory page")
//    public void iShouldLandOnTheInventoryPage() {
//        assertThat(homePage.getDriver().getCurrentUrl(), Matchers.endsWith("/inventory.html"));
//    }
//
//    @Then("I should see an error message that contains {string}")
//    public void iShouldSeeAnErrorMessageThatContains(String expectedError) {
//        assertThat(homePage.getErrorMessage(), Matchers.is(expectedError));
//    }
//
//}
