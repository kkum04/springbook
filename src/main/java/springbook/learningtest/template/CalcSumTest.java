package springbook.learningtest.template;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalcSumTest {
    Calculator calculator;
    String numFilePath;
    @Before public void setUp() {
        this.calculator = new Calculator();
        this.numFilePath = getClass().getResource("/numbers.txt").getPath();
    }

    @Test public void sumOfNumbers() throws IOException {
        assertThat(calculator.calcSumOfNumbers(numFilePath), is(10));
    }

    @Test public void multiplyOfNumbers() throws IOException {
        assertThat(calculator.calcMultiplyOfNumbers(numFilePath), is(24));
    }
}
