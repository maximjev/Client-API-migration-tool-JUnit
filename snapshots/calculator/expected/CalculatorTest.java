package impl;

import exception.CalculatorException;
import org.junit.jupiter.api.*;

import java.util.*;

public class CalculatorTest {

    private Map<String, Integer> map;

    private Calculator calculator;

    @BeforeEach
    public void init() {
        System.out.println("Printing before each test...");
        map = Map.of("1 + 2", 3, "15 - 3", 12);
        calculator = new Calculator();
        calculator.setLog(true);
    }

    @AfterEach
    public void destroy() {
        System.out.println("Printing after each test...");
    }


    @BeforeAll
    public static void setup() {
        System.out.println("Starting Calculator tests...");
    }

    @AfterAll
    public static void teardown() {
        System.out.println("Finishing Calculator tests...");
    }

    @Test
    public void shouldThrowOnDivisionByZero() {
        Assertions.assertThrows(CalculatorException.class, () -> {
            calculator.evaluate("5 / 0");
        });
    }

    @Test
    public void shouldParseExpressions() {
        map.forEach((k, v) -> Assertions.assertEquals((Integer) calculator.evaluate(k), v, "Should be equal"));
    }

    @Test
    public void shouldNotParseExpresion() {
        Assertions.assertThrows(CalculatorException.class, () -> {
            calculator.evaluate("5 % 3");
        });
    }

    @Test
    public void logShouldNotBeEnabled() {
        Assumptions.assumeTrue(calculator.isLog());
    }

    @Test
    public void shouldThrowWhenInvalidArgument() {
        Assertions.assertThrows(CalculatorException.class, () -> {
            System.out.println("Statement for lambda with brackets");
            calculator.evaluate("5 + 3 + 6");
        });
    }

    @Test
    public void shouldReturnValidResultString() {
        String expected = "5 + 3 = 8";
        String value = "5 + 3";
        Assertions.assertEquals(expected, calculator.evaluateString(value), "Should be same string representation");
    }
}
