package calc;

import general.exception.CalculatorException;
import impl.Calculator;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

//Comment associated with class
public class CommentedCalculatorTest {
    // SomeComment

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
    /*
     * Some
     * multiple
     * line
     * comment
     * */

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
            calculator.evaluate("5 + 3 + 6");
        });
    }

    @Test
    public void shouldReturnValidResultString() {
        String expected = "5 + 3 = 8"; // this is expected
        String value = "5 + 3"; // this is expression
        Assertions.assertEquals(expected, calculator.evaluateString(value), "Should be same string representation");
    }

    //Another comment here
    // and here
}
