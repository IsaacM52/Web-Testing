package org.im.simpletests;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class SwagLabsTests {
    private static final String DRIVER_LOCATION = "src/test/resources/drivers/chromedriver.exe";
    private static final String BASE_URL = "https://www.saucedemo.com/";

    private static ChromeDriverService service;
    private WebDriver webDriver;

    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
 //       options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        return options;
    }
    @BeforeAll
    public static void beforeAll() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(DRIVER_LOCATION))
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @BeforeEach
    public void setup() {
        webDriver = new RemoteWebDriver(service.getUrl(), getChromeOptions());
    }

    @AfterEach
    public void afterEach() {
        webDriver.quit();
    }

    @AfterAll
    static void afterAll() {
        service.stop();
    }

    @Test
    @DisplayName("Check that the webdriver works")
    public void checkWebDriver() {
        webDriver.get(BASE_URL);
        Assertions.assertEquals(BASE_URL, webDriver.getCurrentUrl());
        Assertions.assertEquals("Swag Labs", webDriver.getTitle());
    }

    @Test
    @DisplayName("Given I enter a valid username and password, when I click login, then I should land on the inventory page")
    public void successfulLoginTest(){
        // Arrange
        webDriver.get(BASE_URL);
        WebElement usernameField= webDriver.findElement(By.name("user-name"));
        WebElement passwordField= webDriver.findElement(By.name("password"));
        WebElement loginButton= webDriver.findElement(By.id("login-button"));

// Act
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();
        // Assert
        MatcherAssert.assertThat(webDriver.getCurrentUrl(), is(BASE_URL + "inventory.html"));
        //MatcherAssert.assertThat(webDriver.findElement(By.cssSelector("*[data-test=\"title\"]")).getText(), is("Products"));
        WebElement pageLabel = webDriver.findElement(By.cssSelector("*[data-test=\"title\"]"));
        MatcherAssert.assertThat(pageLabel.getText(), is("Products"));


    }


    @DisplayName("Given I am logged in, when I view the inventory page,then I should see the correct number of products")
    public void checkNumberOfProductsOnInventoryPageTest(){
        // Arrange - log in
        webDriver.get(BASE_URL);
        WebElement usernameField= webDriver.findElement(By.name("user-name"));
        WebElement passwordField= webDriver.findElement(By.name("password"));
        WebElement loginButton= webDriver.findElement(By.id("login-button"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        // Assert
        List<WebElement> products = webDriver.findElements(By.className("inventory_item"));
        int productsCount = products.size();
        MatcherAssert.assertThat(productsCount, is(6));
    }



    @Test
    @DisplayName("Given I enter a valid username but invalid password, when I click login, then an error message should be displayed")
    public void invalidPasswordShowsErrorMessageTest() {
        // Arrange
        webDriver.get(BASE_URL);
        WebElement usernameField = webDriver.findElement(By.name("user-name"));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));


        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("wrong_password");
        loginButton.click();


        WebElement errorMessage = webDriver.findElement(By.cssSelector("[data-test='error']"));
        MatcherAssert.assertThat(errorMessage.getText(), containsString("Username and password do not match any user in this service"));

        // check that it still on login page
        MatcherAssert.assertThat(webDriver.getCurrentUrl(), is(BASE_URL));

    }

    @Test
    @DisplayName("Given I am logged in, when I view the inventory page, I should see the correct number of products (with wait)")

    public void checkNumberOfProductsOnInventnoryPage_WithWait(){

        Wait<WebDriver> webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        webDriver.get(BASE_URL);
        WebElement usernameField= webDriver.findElement(By.name("user-name"));
        WebElement passwordField= webDriver.findElement(By.name("password"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce", Keys.ENTER);
        webDriverWait.until(driver -> driver.getCurrentUrl().contains("/inventory"));
        MatcherAssert.assertThat(webDriver.getCurrentUrl(), is("https://www.saucedemo.com/inventory.html"));

    }



    @Test
    @DisplayName("Given I am logged in, when I add two items to the cart, then the cart shows both items")
    public void addTwoItemsToCartTest() {
        webDriver.get(BASE_URL);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

        // Login
        webDriver.findElement(By.name("user-name")).sendKeys("standard_user");
        webDriver.findElement(By.name("password")).sendKeys("secret_sauce");
        webDriver.findElement(By.id("login-button")).click();

        // Add items
        webDriver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        webDriver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();

        // Assert
        WebElement cartBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".shopping_cart_badge")));
        MatcherAssert.assertThat(cartBadge.getText(), is("2"));

        // Navigate to cart
        webDriver.findElement(By.className("shopping_cart_link")).click();

        // Wait for cart items to load
        List<WebElement> cartItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".inventory_item_name")));
        MatcherAssert.assertThat(cartItems.size(), is(2));
    }

    @Test
    @DisplayName("Checkout flow should complete successfully when valid data is entered")
    public void checkoutFlowTest() {
        webDriver.get(BASE_URL);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

        // Login (no waits)
        webDriver.findElement(By.name("user-name")).sendKeys("standard_user");
        webDriver.findElement(By.name("password")).sendKeys("secret_sauce");
        webDriver.findElement(By.id("login-button")).click();

        // Add product
        webDriver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        // Go to cart
        webDriver.findElement(By.className("shopping_cart_link")).click();

        // Checkout
        webDriver.findElement(By.id("checkout")).click();

        // Fill checkout form
        webDriver.findElement(By.id("first-name")).sendKeys("Ben");
        webDriver.findElement(By.id("last-name")).sendKeys("Benji");
        webDriver.findElement(By.id("postal-code")).sendKeys("12345");
        webDriver.findElement(By.id("continue")).click();

        // Wait only here: overview page loads and item must be visible
        WebElement overviewItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".inventory_item_name")));
        MatcherAssert.assertThat(overviewItem.getText(), is("Sauce Labs Backpack"));


        //verify item total price ---
        WebElement itemTotal = webDriver.findElement(By.cssSelector(".summary_subtotal_label"));
        MatcherAssert.assertThat(itemTotal.getText(), containsString("29.99"));


        // Finish
        webDriver.findElement(By.id("finish")).click();

        // Final confirmation (requires waiting)
        WebElement completeHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".complete-header")));
        MatcherAssert.assertThat(completeHeader.getText(), is("Thank you for your order!"));
    }


}

