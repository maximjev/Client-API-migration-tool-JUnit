package impl;

import exception.CalculatorException;
import org.junit.*;

import java.util.*;

public class CalculatorTest {

    private Map<String, Integer> map;

    private Calculator calculator;

    @Before
    public void init() {
        System.out.println("Printing before each test...");
        map = Map.of("1 + 2", 3, "15 - 3", 12);
        calculator = new Calculator();
        calculator.setLog(true);
    }

    @After
    public void destroy() {
        System.out.println("Printing after each test...");
    }


    @BeforeClass
    public static void setup() {
        System.out.println("Starting Calculator tests...");
    }

    @AfterClass
    public static void teardown() {
        System.out.println("Finishing Calculator tests...");
    }

    @Test(expected = CalculatorException.class)
    public void shouldThrowOnDivisionByZero() {
        calculator.evaluate("5 / 0");
    }

    @Test
    public void shouldParseExpressions() {
        map.forEach((k, v) -> Assert.assertEquals("Should be equal", (Integer) calculator.evaluate(k), v));
    }

    @Test(expected = CalculatorException.class)
    public void shouldNotParseExpresion() {
        calculator.evaluate("5 % 3");
    }

    @Test
    public void logShouldNotBeEnabled() {
        Assume.assumeTrue(calculator.isLog());
    }

    @Test(expected = CalculatorException.class)
    public void shouldThrowWhenInvalidArgument() {
        System.out.println("Statement for lambda with brackets");
        calculator.evaluate("5 + 3 + 6");
    }

    @Test
    public void shouldReturnValidResultString() {
        String expected = "5 + 3 = 8";
        String value = "5 + 3";
        Assert.assertEquals("Should be same string representation", expected, calculator.evaluateString(value));
    }
}
