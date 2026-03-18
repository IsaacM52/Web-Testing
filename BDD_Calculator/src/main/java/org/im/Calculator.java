package org.im;

public class Calculator {
    private int num1;
    private  int num2;

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public int add () {
        return num1 + num2;
    }

    public int subtract() {
        return num1 -num2;
    }

//    public int divide() {
//        return num1 / num2;
//    }

    public int divide() {
        if (num2 == 0) {
            throw new ArithmeticException("Divisor cannot be zero.");
        }
        return num1 / num2;
    }

}
