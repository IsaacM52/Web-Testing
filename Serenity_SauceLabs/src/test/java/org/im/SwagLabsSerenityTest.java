package org.im;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.thucydides.core.annotations.Managed;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@ExtendWith(SerenityJUnit5Extension.class)

public class SwagLabsSerenityTest {
    @Managed
    WebDriver webDriver;

    private static final String BASE_URL = "https://www.saucedemo.com/";

    @Test
    @DisplayName("Check that the webdriver works")
    public void checkWebDriver() {
        webDriver.get(BASE_URL);
        Assertions.assertEquals(BASE_URL, webDriver.getCurrentUrl());
        Assertions.assertEquals("Swag Labs", webDriver.getTitle());
    }

    @Test
    @DisplayName("Successful login with valid credentials should redirect to inventory page")
    public void successfulLoginTest() {
        webDriver.get(BASE_URL);

        WebElement usernameField = webDriver.findElement(By.name("user-name"));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        assertThat(webDriver.getCurrentUrl(), Matchers.endsWith("/inventory.html"));
    }

    @DisplayName("Given I am logged in, when I view the inventory page,then I should see the correct number of products")
    public void checkNumberOfProductsOnInventoryPageTest(){
        // Arrange - log in
        webDriver.get(BASE_URL);
        WebElement usernameField= webDriver.findElement(org.openqa.selenium.By.name("user-name"));
        WebElement passwordField= webDriver.findElement(org.openqa.selenium.By.name("password"));
        WebElement loginButton= webDriver.findElement(org.openqa.selenium.By.id("login-button"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        // Assert
        List<WebElement> products = webDriver.findElements(org.openqa.selenium.By.className("inventory_item"));
        int productsCount = products.size();
        MatcherAssert.assertThat(productsCount, is(6));
    }


    @Test
    @DisplayName("Given I enter a valid username but invalid password, when I click login, then an error message should be displayed")
    public void invalidPasswordShowsErrorMessageTest() {
        // Arrange
        webDriver.get(BASE_URL);
        WebElement usernameField = webDriver.findElement(org.openqa.selenium.By.name("user-name"));
        WebElement passwordField = webDriver.findElement(org.openqa.selenium.By.name("password"));
        WebElement loginButton = webDriver.findElement(org.openqa.selenium.By.id("login-button"));


        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("wrong_password");
        loginButton.click();


        WebElement errorMessage = webDriver.findElement(org.openqa.selenium.By.cssSelector("[data-test='error']"));
        MatcherAssert.assertThat(errorMessage.getText(), containsString("Username and password do not match any user in this service"));

        // check that it still on login page
        MatcherAssert.assertThat(webDriver.getCurrentUrl(), is(BASE_URL));

    }

    @Test
    @DisplayName("Given I am logged in, when I view the inventory page, I should see the correct number of products (with wait)")

    public void checkNumberOfProductsOnInventnoryPage_WithWait(){

        Wait<WebDriver> webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        webDriver.get(BASE_URL);
        WebElement usernameField= webDriver.findElement(org.openqa.selenium.By.name("user-name"));
        WebElement passwordField= webDriver.findElement(org.openqa.selenium.By.name("password"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce", Keys.ENTER);
        webDriverWait.until(driver -> driver.getCurrentUrl().contains("/inventory"));
        MatcherAssert.assertThat(webDriver.getCurrentUrl(), is("https://www.saucedemo.com/inventory.html"));

    }



//    @Test
//    @DisplayName("Given I am logged in, when I add two items to the cart, then the cart shows both items")
//    public void addTwoItemsToCartTest() {
//        webDriver.get(BASE_URL);
//        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
//
//        // Login
//        webDriver.findElement(org.openqa.selenium.By.name("user-name")).sendKeys("standard_user");
//        webDriver.findElement(org.openqa.selenium.By.name("password")).sendKeys("secret_sauce");
//        webDriver.findElement(org.openqa.selenium.By.id("login-button")).click();
//
//        // Add items
//        webDriver.findElement(org.openqa.selenium.By.id("add-to-cart-sauce-labs-backpack")).click();
//        webDriver.findElement(org.openqa.selenium.By.id("add-to-cart-sauce-labs-bike-light")).click();
//
//        // Assert
//        WebElement cartBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.cssSelector(".shopping_cart_badge")));
//        MatcherAssert.assertThat(cartBadge.getText(), is("2"));
//
//        // Navigate to cart
//        webDriver.findElement(org.openqa.selenium.By.className("shopping_cart_link")).click();
//
//        // Wait for cart items to load
//        List<WebElement> cartItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(org.openqa.selenium.By.cssSelector(".inventory_item_name")));
//        MatcherAssert.assertThat(cartItems.size(), is(2));
//    }

//    @Test
//    @DisplayName("Checkout flow should complete successfully when valid data is entered")
//    public void checkoutFlowTest() {
//        webDriver.get(BASE_URL);
//        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
//
//        // Login (no waits)
//        webDriver.findElement(org.openqa.selenium.By.name("user-name")).sendKeys("standard_user");
//        webDriver.findElement(org.openqa.selenium.By.name("password")).sendKeys("secret_sauce");
//        webDriver.findElement(org.openqa.selenium.By.id("login-button")).click();
//
//        // Add product
//        webDriver.findElement(org.openqa.selenium.By.id("add-to-cart-sauce-labs-backpack")).click();
//
//        // Go to cart
//        webDriver.findElement(org.openqa.selenium.By.className("shopping_cart_link")).click();
//
//        // Checkout
//        webDriver.findElement(org.openqa.selenium.By.id("checkout")).click();
//
//        // Fill checkout form
//        webDriver.findElement(org.openqa.selenium.By.id("first-name")).sendKeys("Ben");
//        webDriver.findElement(org.openqa.selenium.By.id("last-name")).sendKeys("Benji");
//        webDriver.findElement(org.openqa.selenium.By.id("postal-code")).sendKeys("12345");
//        webDriver.findElement(org.openqa.selenium.By.id("continue")).click();
//
//        // Wait only here: overview page loads and item must be visible
//        WebElement overviewItem = wait.until(ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.cssSelector(".inventory_item_name")));
//        MatcherAssert.assertThat(overviewItem.getText(), is("Sauce Labs Backpack"));
//
//
//        //verify item total price ---
//        WebElement itemTotal = webDriver.findElement(org.openqa.selenium.By.cssSelector(".summary_subtotal_label"));
//        MatcherAssert.assertThat(itemTotal.getText(), containsString("29.99"));
//
//
//        // Finish
//        webDriver.findElement(org.openqa.selenium.By.id("finish")).click();
//
//        // Final confirmation (requires waiting)
//        WebElement completeHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.cssSelector(".complete-header"))
//        );
//        MatcherAssert.assertThat(completeHeader.getText(), is("Thank you for your order!"));
//    }


}
