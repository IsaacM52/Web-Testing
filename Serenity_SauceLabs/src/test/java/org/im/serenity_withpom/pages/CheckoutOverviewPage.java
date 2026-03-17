package org.im.serenity_withpom.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://www.saucedemo.com/checkout-step-two.html")
public class CheckoutOverviewPage extends PageObject {

    @FindBy(id = "finish")
    private WebElementFacade finishButton;

    @FindBy(id = "cancel")
    private WebElementFacade cancelButton;

    @FindBy(className = "summary_total_label")
    private WebElementFacade totalPriceLabel;

    public void clickFinishButton() {
        finishButton.click();
    }

    public void clickCancelButton() {
        cancelButton.click();
    }

    public String getTotalPriceText() {
        return totalPriceLabel.getText();
    }
}
