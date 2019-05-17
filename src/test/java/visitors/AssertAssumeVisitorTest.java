package visitors;

import org.junit.jupiter.api.Test;
import tool.JUnitMigrationTool;
import utils.TestUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.*;
import static utils.TestUtils.*;
import static utils.TestConstants.*;

public class AssertAssumeVisitorTest {

    private JUnitMigrationTool tool = new JUnitMigrationTool();

    private static final Set<String> ASSERT_METHODS = new HashSet<>(
            Arrays.asList("assertFalse", "assertEquals", "assertNotEquals", "assertArrayEquals", "assertNotSame",
                    "assertSame", "assertTrue", "assertNull", "assertNotNull"));

    private static final Set<String> ASSUME_METHODS = new HashSet<>(
            Arrays.asList("assumeTrue", "assumeFalse", "assumeNoException", "assumeNotNull", "assumeNull")
    );

    @Test
    public void assertStringArgumentSwapTest() {
        String expected = Stream.of("Assertions.assertEquals(1, 1, \"message\");")
                .map(TestUtils::methodWrap)
                .map(TestUtils::classWrap)
                .map(c -> appendImport(c, NEW_IMPORT, ASSERTIONS_STRING))
                .map(TestUtils::prettyPrint)
                .collect(joining());

        String original = Stream.of("Assert.assertEquals(\"message\", 1, 1);")
                .map(TestUtils::methodWrap)
                .map(TestUtils::classWrap)
                .map(c -> appendImport(c, OLD_IMPORT, ASSERT_STRING))
                .collect(joining());

        assertEquals(expected, tool.migrate(original));
    }

    @Test
    public void assertShouldNotSwapMessageTest() {
        String expected = Stream.of("Assertions.assertEquals(1, 1);")
                .map(TestUtils::methodWrap)
                .map(TestUtils::classWrap)
                .map(c -> appendImport(c, NEW_IMPORT, ASSERTIONS_STRING))
                .map(TestUtils::prettyPrint)
                .collect(joining());

        String original = Stream.of("Assert.assertEquals(1, 1);")
                .map(TestUtils::methodWrap)
                .map(TestUtils::classWrap)
                .map(c -> appendImport(c, OLD_IMPORT, ASSERT_STRING))
                .collect(joining());

        assertEquals(expected, tool.migrate(original));
    }

    @Test
    public void assertMethodsEachTest() {
        ASSERT_METHODS.forEach(method -> {
            String expected = Stream.of(String.format("String m = \"abc\";\n " +
                    "int a = 5; \n " +
                    "int b = 6; \n " +
                    "Assertions.%s(a, b, m);", method))
                    .map(TestUtils::methodWrap)
                    .map(TestUtils::classWrap)
                    .map(c -> appendImport(c, NEW_IMPORT, ASSERTIONS_STRING))
                    .map(TestUtils::prettyPrint)
                    .collect(joining());

            String original = Stream.of(String.format("String m = \"abc\";\n " +
                    "int a = 5; \n " +
                    "int b = 6; \n " +
                    "Assert.%s(m, a, b);", method))
                    .map(TestUtils::methodWrap)
                    .map(TestUtils::classWrap)
                    .map(c -> appendImport(c, OLD_IMPORT, ASSERT_STRING))
                    .collect(joining());

            assertEquals(expected, tool.migrate(original));
        });
    }

    @Test
    public void assumeMethodsEachTest() {
        ASSUME_METHODS.forEach(method -> {
            String expected = Stream.of(String.format("String m = \"abc\";\n " +
                    "int a = 5; \n " +
                    "int b = 6; \n " +
                    "Assumptions.%s(a, b, m);", method))
                    .map(TestUtils::methodWrap)
                    .map(TestUtils::classWrap)
                    .map(c -> appendImport(c, NEW_IMPORT, ASSERTIONS_STRING))
                    .map(TestUtils::prettyPrint)
                    .collect(joining());

            String original = Stream.of(String.format("String m = \"abc\";\n " +
                    "int a = 5; \n " +
                    "int b = 6; \n " +
                    "Assume.%s(m, a, b);", method))
                    .map(TestUtils::methodWrap)
                    .map(TestUtils::classWrap)
                    .map(c -> appendImport(c, OLD_IMPORT, ASSERT_STRING))
                    .collect(joining());

            assertEquals(expected, tool.migrate(original));
        });
    }

    @Test
    public void assertVariableArgumentSwapTest() {
        String expected = Stream.of(" String a = \"message\";\n Assertions.assertEquals(1, 1, a);")
                .map(TestUtils::methodWrap)
                .map(TestUtils::classWrap)
                .map(c -> appendImport(c, NEW_IMPORT, ASSERTIONS_STRING))
                .map(TestUtils::prettyPrint)
                .collect(joining());

        String original = Stream.of("String a = \"message\";\n Assert.assertEquals(a, 1, 1);")
                .map(TestUtils::methodWrap)
                .map(TestUtils::classWrap)
                .map(c -> appendImport(c, OLD_IMPORT, ASSERT_STRING))
                .collect(joining());

        assertEquals(expected, tool.migrate(original));
    }

    @Test
    public void assertThreeStringArgumentsTest() {
        String expected = Stream.of(" String a = \"message\";\n " +
                "Assertions.assertEquals(\"abc\", \"abc\", a);")
                .map(TestUtils::methodWrap)
                .map(TestUtils::classWrap)
                .map(c -> appendImport(c, NEW_IMPORT, ASSERTIONS_STRING))
                .map(TestUtils::prettyPrint)
                .collect(joining());

        String original = Stream.of("String a = \"message\";\n " +
                "Assert.assertEquals(a, \"abc\", \"abc\");")
                .map(TestUtils::methodWrap)
                .map(TestUtils::classWrap)
                .map(c -> appendImport(c, OLD_IMPORT, ASSERT_STRING))
                .collect(joining());

        assertEquals(expected, tool.migrate(original));
    }

    @Test
    public void assertStaticImportTest() {
        String expected = Stream.of(" String a = \"message\";\n assertEquals(1, 1, a);")
                .map(TestUtils::methodWrap)
                .map(TestUtils::classWrap)
                .map(c -> appendStaticImport(c, NEW_IMPORT, ASSERTIONS_STRING))
                .map(TestUtils::prettyPrint)
                .collect(joining());

        String original = Stream.of("String a = \"message\";\n assertEquals(a, 1, 1);")
                .map(TestUtils::methodWrap)
                .map(TestUtils::classWrap)
                .map(c -> appendStaticImport(c, OLD_IMPORT, ASSERT_STRING))
                .collect(joining());

        assertEquals(expected, tool.migrate(original));
    }
}
