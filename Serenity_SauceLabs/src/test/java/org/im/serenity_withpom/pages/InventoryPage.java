package org.im.serenity_withpom.pages;


import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

import java.util.List;

@DefaultUrl("https://www.saucedemo.com/inventory.html")
public class InventoryPage extends PageObject {

    @FindBy(className = "inventory_item")
    private List<WebElementFacade> inventoryItems;

    @FindBy(className = "title")
    private WebElementFacade pageTitle;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElementFacade addBackpackToCartButton;

    @FindBy(className = "shopping_cart_link")
    private WebElementFacade cartIcon;

    @FindBy(id = "remove-sauce-labs-backpack")
    private WebElementFacade removeBackpackButton;



    public int getNumberOfProducts() {
        return inventoryItems.size();
    }

    public boolean isInventoryPageLoaded() {
        return pageTitle.isDisplayed();
    }

    public String getPageTitle() {
        return pageTitle.getText();
    }

    public void addBackpackToCart() {
        addBackpackToCartButton.click();
    }

    public void removeBackpackFromCart() {
        removeBackpackButton.click();
    }

    public void clickCartIcon() {
        cartIcon.click();
    }


}


