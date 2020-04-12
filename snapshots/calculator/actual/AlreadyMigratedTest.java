package test;

import impl.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class AlreadyMigratedTest {

    private Map<String, Integer> map;

    private Calculator calculator;

    @BeforeAll
    public void init() {
        System.out.println("Printing before each test...");
        map = Map.of("1 + 2", 3, "15 - 3", 12);
        calculator = new Calculator();
        calculator.setLog(true);
    }

    @Test
    public void shouldReturnValidResultString() {
        String expected = "5 + 3 = 8";
        String value = "5 + 3";
        Assertions.assertEquals(expected, calculator.evaluateString(value), "Should be same string representation");
    }

    @Test
    public void shouldParseExpressions() {
        map.forEach((k, v) -> Assertions.assertEquals((Integer) calculator.evaluate(k), v, "Should be equal"));
    }

    @Test
    public void logShouldNotBeEnabled() {
        Assumptions.assumeTrue(calculator.isLog());
    }

}
