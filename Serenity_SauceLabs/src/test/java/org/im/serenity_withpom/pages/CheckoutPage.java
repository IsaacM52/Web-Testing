package org.im.serenity_withpom.pages;


import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://www.saucedemo.com/checkout-step-one.html")
public class CheckoutPage extends PageObject {

    @FindBy(id = "first-name")
    private WebElementFacade firstNameField;

    @FindBy(id = "last-name")
    private WebElementFacade lastNameField;

    @FindBy(id = "postal-code")
    private WebElementFacade postalCodeField;

    @FindBy(id = "continue")
    private WebElementFacade continueButton;

    @FindBy(id = "cancel")
    private WebElementFacade cancelButton;

    public void enterFirstName(String firstName) {
        firstNameField.type(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameField.type(lastName);
    }

    public void enterPostalCode(String postalCode) {
        postalCodeField.type(postalCode);
    }

    public void clickContinue() {
        continueButton.click();
    }

    public void clickCancel() {
        cancelButton.click();
    }
}

//public class CheckoutPage {
//
//}
