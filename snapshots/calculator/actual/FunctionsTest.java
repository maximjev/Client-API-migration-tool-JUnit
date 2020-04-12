package impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionsTest {

    private Calculator calculator;

    @Before
    public void initCalculator() {
        calculator = new Calculator();
    }

    @Test
    public void shouldSum() {
        assertEquals(calculator.sum(5, 6), 11);
    }

    @Test
    public void shouldSub() {
        assertEquals(calculator.sub(10, 5), 5);
    }

    @Test
    public void shouldDivide() {
        assertEquals(calculator.divide(10, 2), 5);
    }

    @Test
    public void shouldMultiply() {
        assertEquals(calculator.multiply(10, 10), 100);
    }
}
