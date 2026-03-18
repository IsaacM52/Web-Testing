Feature: Calculator

  Simple calculator which takes two numbers


  @HappyPath
  Scenario: Addition
    Given I have a calculator
    And I enter 5 and 2 into the calculator
    When I press add
    Then the result should be 7


  @HappyPath
  Scenario Outline: Subtract
    Given I have a calculator
    And I enter <input1> and <input2> into the calculator
    When I press subtract
    Then the result should be <result>
    Examples:

      |input1|input2|result|

      |1     |1     |0     |

      |0     |1     |-1    |

      |1000  |1     |999   |


  @HappyPath
  Scenario: Divide
    And I enter 6 and 2 into the calculator
    When I press divide
    Then the result should be 3

  @SadPath
  Scenario Outline: Divide By Zero
    And I enter <input1> and 0 into the calculator
    When I press divide
    Then a ArithmeticException should be thrown
    And the exception should have the message "Divisor cannot be zero."
    Examples:
      | input |
      | 1     |
      | 6     |