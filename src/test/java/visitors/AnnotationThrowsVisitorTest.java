package visitors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tool.JUnitMigrationTool;
import utils.TestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.lang.String.*;
import static java.util.stream.Collectors.*;
import static utils.TestUtils.*;
import static utils.TestConstants.*;

public class AnnotationThrowsVisitorTest {

    private JUnitMigrationTool tool = new JUnitMigrationTool();

    String expectedBody = format("Assertions.assertThrows(%s, () -> {\n int a = 5;\n});", EXCEPTION_STRING);

    @Test
    public void throwsExceptionTest() {

        String expected = Stream.of(expectedBody)
                .map(TestUtils::methodWrap)
                .map(c -> appendAnnotation(c, TEST_STRING))
                .map(TestUtils::classWrap)
                .map(c -> appendImport(c, NEW_IMPORT, ASSERTIONS_STRING))
                .map(c -> appendImport(c, NEW_IMPORT, TEST_STRING))
                .map(TestUtils::prettyPrint)
                .collect(joining());

        String value = Stream.of(methodWrap("int a = 5;\n"))
                .map(c -> appendAnnotation(c, TEST_STRING, EXPECTED_STRING, EXCEPTION_STRING))
                .map(TestUtils::classWrap)
                .map(c -> appendImport(c, NEW_IMPORT, TEST_STRING))
                .map(TestUtils::prettyPrint)
                .collect(joining());

        String result = prettyPrint(tool.migrate(value));

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void throwsExceptionWithParamsTest() {
        Map<String, String> params = new HashMap<>();
        params.put(EXPECTED_STRING, EXCEPTION_STRING);
        params.put(TIMEOUT_STRING, "5");

        String expected = Stream.of(expectedBody)
                .map(TestUtils::methodWrap)
                .map(c -> appendAnnotation(c, TEST_STRING))
                .map(TestUtils::classWrap)
                .map(c -> appendImport(c, NEW_IMPORT, ASSERTIONS_STRING))
                .map(c -> appendImport(c, NEW_IMPORT, TEST_STRING))
                .map(TestUtils::prettyPrint)
                .collect(joining());

        String value = Stream.of(methodWrap("int a = 5;\n"))
                .map(c -> appendAnnotations(c, TEST_STRING, params))
                .map(TestUtils::classWrap)
                .map(c -> appendImport(c, NEW_IMPORT, TEST_STRING))
                .map(TestUtils::prettyPrint)
                .collect(joining());

        String result = prettyPrint(tool.migrate(value));
        Assertions.assertEquals(expected, result);
    }
}
