package org.im.serenity_withpom.pages;


import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://www.saucedemo.com/checkout-complete.html")
public class FinishPage extends PageObject {

    @FindBy(className = "complete-header")
    private WebElementFacade completeHeaderMessage;

    @FindBy(id = "back-to-products")
    private WebElementFacade backHomeButton;

    public String getCompleteHeaderMessage() {
        return completeHeaderMessage.getText();
    }

    public boolean isOrderCompleteMessageDisplayed() {
        return completeHeaderMessage.isDisplayed();
    }

    public void clickBackHome() {
        backHomeButton.click();
    }
}


