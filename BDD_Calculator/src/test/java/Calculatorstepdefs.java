//import io.cucumber.java.PendingException;
//import io.cucumber.java.en.And;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import org.im.Calculator;
//import org.junit.jupiter.api.Assertions;
//
//public class Calculatorstepdefs {
//    private Calculator calculator;
//    private int result;
//    private int result2;
//    private Exception exception;
//
//    @Given("I have a calculator")
//    public void iHaveACalculator() {
//
//        calculator = new Calculator();
//    }
//
//    @And("I enter {int} and {int} into the calculator")
//    public void iEnterAndIntoTheCalculator(int num1, int num2) {
//        calculator.setNum1(num1);
//        calculator.setNum2(num2);
//    }
//
//    @When("I press add")
//    public void iPressAdd() {
//        result = calculator.add();
//    }
//
//    @When("I press subtract")
//    public void iPressSubtract() {
//        result = calculator.subtract();
//    }
//
////    @When("I press divide")
////    public void iPressDivide() {
////        result = calculator.divide();
////    }
//
//
//    @When("I press divide")
//    public void iPressDivide() {
//        try {
//            result = calculator.divide();
//        } catch (Exception e) {
//            exception = e;
//        }
//    }
//
//
//    @Then("the result should be {int}")
//    public void theResultShouldBe(int  ) {
//        Assertions.assertEquals(, result);
//    }
//
//    @Then("a ArithmeticException should be thrown")
//    public void aArithmeticExceptionShouldBeThrown() {
//        Assertions.assertNotNull(exception, "No exception was thrown");
//        Assertions.assertTrue(exception instanceof ArithmeticException,
//                "Expected ArithmeticException but got " + exception.getClass());
//    }
//
//
//    @And("the exception should have the message {string}")
//    public void theExceptionShouldHaveTheMessage(String expectedMessage) {
//        Assertions.assertEquals(expectedMessage, exception.getMessage());
//    }
//
//
//
//
//
//    @Then("the result should be {int}")
//    public void theResultShouldBe(int expectedResult) {
//        Assertions.assertEquals(expectedResult, result);
//    }
//
//
//}
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.im.Calculator;
import org.junit.jupiter.api.Assertions;

public class Calculatorstepdefs {

    private Calculator calculator;
    private int result;
    private Exception exception;

    @Given("I have a calculator")
    public void iHaveACalculator() {
        calculator = new Calculator();
    }

    @And("I enter {int} and {int} into the calculator")
    public void iEnterAndIntoTheCalculator(int num1, int num2) {
        calculator.setNum1(num1);
        calculator.setNum2(num2);
    }

    @When("I press add")
    public void iPressAdd() {
        result = calculator.add();
    }

    @When("I press subtract")
    public void iPressSubtract() {
        result = calculator.subtract();
    }

    @When("I press divide")
    public void iPressDivide() {
        try {
            result = calculator.divide();
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the result should be {int}")
    public void theResultShouldBe(int expectedResult) {
        Assertions.assertEquals(expectedResult, result);
    }

    @Then("a ArithmeticException should be thrown")
    public void aArithmeticExceptionShouldBeThrown() {
        Assertions.assertNotNull(exception, "No exception was thrown");
        Assertions.assertTrue(exception instanceof ArithmeticException,
                "Expected ArithmeticException but got " + exception.getClass());
    }

    @And("the exception should have the message {string}")
    public void theExceptionShouldHaveTheMessage(String expectedMessage) {
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}