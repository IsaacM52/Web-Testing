package org.im.serenity_withpom.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

import java.util.List;

@DefaultUrl("https://www.saucedemo.com/cart.html")
public class CartPage extends PageObject {

    @FindBy(className = "cart_item")
    private List<WebElementFacade> cartItems;

    @FindBy(className = "inventory_item_name")
    private WebElementFacade itemName;

    public int getNumberOfItemsInCart() {
        return cartItems.size();
    }

    public String getFirstItemName() {
        return itemName.getText();
    }
}


