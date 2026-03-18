package org.im.serenity_withpom.Test;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.thucydides.core.annotations.Managed;
import org.hamcrest.Matchers;
import org.im.serenity_withpom.pages.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;


@ExtendWith(SerenityJUnit5Extension.class)
public class SwagLabsSerenityTestPOM {
    @Managed
    HomePage homePage;
    InventoryPage inventoryPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    CheckoutOverviewPage checkoutOverviewPage;
    FinishPage finishPage;


    @Test
    @DisplayName("Successful login with valid credentials should redirect to inventory page")
    public void successfulLoginTest() {
        //Given
        homePage.open();
        // when
        homePage.enterUserName("standard_user");
        homePage.enterPassword("secret_sauce");
        homePage.clickLoginButton();

        //Then
        assertThat(homePage.getDriver().getCurrentUrl(), Matchers.endsWith("/inventory.html"));

    }

    @Test
    @DisplayName("Given I enter a valid username but invalid password, when I click login, then an error message should be displayed")
    public void invalidPasswordShowsErrorMessageTest() {

        // Arrange
        homePage.open();
        homePage.enterUserName("standard_user");
        homePage.enterPassword("wrong_password");

        // Act
        homePage.clickLoginButton();

        // Assert: error message is displayed
        assertThat(homePage.isErrorMessageDisplayed(), is(true));

        // Assert: the error text is correct
        assertThat(
                homePage.getErrorMessage(),
                containsString("Username and password do not match any user in this service")
        );

        // Assert: still on login page
        assertThat(homePage.getDriver().getCurrentUrl(), is("https://www.saucedemo.com/"));
    }


    @Test
    @DisplayName("Given I am logged in, when I view the inventory page, then I should see the correct number of products")
    public void checkNumberOfProductsOnInventoryPageTest() {

        // Given
        homePage.open();
        homePage.enterUserName("standard_user");
        homePage.enterPassword("secret_sauce");
        homePage.clickLoginButton();

        // When
        int productCount = inventoryPage.getNumberOfProducts();

        // Then
        assertThat(productCount, is(6));
    }


    @Test
    @DisplayName("Given I am logged in, when I add an item to the cart, then the item should appear in the cart")
    public void addItemToCartTest() {

        // Given: logged in
        homePage.open();
        homePage.enterUserName("standard_user");
        homePage.enterPassword("secret_sauce");
        homePage.clickLoginButton();

        // When: Add backpack to cart
        inventoryPage.addBackpackToCart();
        inventoryPage.clickCartIcon();


        // Then: Verify item is in cart
        assertThat(cartPage.getNumberOfItemsInCart(), is(1));
        assertThat(cartPage.getFirstItemName(), is("Sauce Labs Backpack"));

    }

    @Test
    @DisplayName("Given I have items in the cart, when I click checkout, then I go to the checkout page")
    public void checkoutButtonNavigatesToCheckoutPage() {

        // Login first
        homePage.open();
        homePage.enterUserName("standard_user");
        homePage.enterPassword("secret_sauce");
        homePage.clickLoginButton();

        // Add item
        inventoryPage.addBackpackToCart();
        inventoryPage.clickCartIcon();

        // When
        cartPage.clickCheckout();

        // Then
        assertThat(cartPage.getDriver().getCurrentUrl(), containsString("checkout-step-one.html"));
    }


    @Test
    @DisplayName("Given I have an item in the cart, when I checkout, then I should reach the checkout info page")
    public void checkoutFlowTest() {

        // Login
        homePage.open();
        homePage.enterUserName("standard_user");
        homePage.enterPassword("secret_sauce");
        homePage.clickLoginButton();

        // Add item
        inventoryPage.addBackpackToCart();
        inventoryPage.clickCartIcon();

        // Proceed to checkout
        cartPage.clickCheckout();

        // Fill out checkout form
        checkoutPage.enterFirstName("John");
        checkoutPage.enterLastName("Doe");
        checkoutPage.enterPostalCode("SW1A1AA");
        checkoutPage.clickContinue();

        // Assert: verify next page loads correctly
        assertThat(checkoutPage.getDriver().getCurrentUrl(), containsString("checkout-step-two.html"));
    }


    @Test
    @DisplayName("Given I checkout, when I finish the order, then I should see the order confirmation message")
    public void finishOrderTest() {

        // Login
        homePage.open();
        homePage.enterUserName("standard_user");
        homePage.enterPassword("secret_sauce");
        homePage.clickLoginButton();

        // Add item
        inventoryPage.addBackpackToCart();
        inventoryPage.clickCartIcon();

        // Checkout step one
        cartPage.clickCheckout();
        checkoutPage.enterFirstName("John");
        checkoutPage.enterLastName("Doe");
        checkoutPage.enterPostalCode("SW1A1AA");
        checkoutPage.clickContinue();

        // Checkout overview → Finish
        checkoutOverviewPage.clickFinishButton();

        // Assert order complete
        assertThat(finishPage.isOrderCompleteMessageDisplayed(), is(true));
        assertThat(finishPage.getCompleteHeaderMessage(), is("Thank you for your order!"));
    }
}










