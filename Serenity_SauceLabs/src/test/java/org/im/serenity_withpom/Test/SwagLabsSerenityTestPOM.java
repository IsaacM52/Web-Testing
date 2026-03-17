package org.im.serenity_withpom.Test;



import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.thucydides.core.annotations.Managed;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.im.serenity_withpom.pages.CartPage;
import org.im.serenity_withpom.pages.HomePage;
import org.im.serenity_withpom.pages.InventoryPage;
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




//    @Test
//    @DisplayName("Check that the webdriver works")
//    public void checkWebDriver() {
//        webDriver.get(BASE_URL);
//        Assertions.assertEquals(BASE_URL, webDriver.getCurrentUrl());
//        Assertions.assertEquals("Swag Labs", webDriver.getTitle());
//    }

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









//    public void checkNumberOfProductsOnInventnoryPage_WithWait(){
//
//        Wait<WebDriver> webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
//        webDriver.get(BASE_URL);
//        WebElement usernameField= webDriver.findElement(org.openqa.selenium.By.name("user-name"));
//        WebElement passwordField= webDriver.findElement(org.openqa.selenium.By.name("password"));
//        usernameField.sendKeys("standard_user");
//        passwordField.sendKeys("secret_sauce", Keys.ENTER);
//        webDriverWait.until(driver -> driver.getCurrentUrl().contains("/inventory"));
//        MatcherAssert.assertThat(webDriver.getCurrentUrl(), is("https://www.saucedemo.com/inventory.html"));
//
//    }

}
