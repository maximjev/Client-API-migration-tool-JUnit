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

public class AnnotationThrowsVisitorTest {

    private JUnitMigrationTool tool = new JUnitMigrationTool();


    String annotation = "Test";
    String exception = "IllegalArgumentException.class";
    String annotationImport = "import org.junit.jupiter.api.";
    String expectedBody = format("Assertions.assertThrows(%s, () -> {\n});", exception);

    @Test
    public void throwsExceptionTest() {

        String expected = Stream.of(expectedBody)
                .map(TestUtils::methodWrap)
                .map(c -> appendEmptyAnnotation(c, annotation))
                .map(TestUtils::classWrap)
                .map(c -> appendImport(c, annotationImport, "Assertions"))
                .map(c -> appendImport(c, annotationImport, annotation))
                .map(TestUtils::prettyPrint)
                .collect(joining());

        String value = Stream.of(methodWrap(""))
                .map(c -> appendAnnotation(c, annotation, "expected", exception))
                .map(TestUtils::classWrap)
                .map(c -> appendImport(c, annotationImport, annotation))
                .map(TestUtils::prettyPrint)
                .collect(joining());

        String result = tool.migrate(value);

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void throwsExceptionWithParamsTest() {
        Map<String, String> params = new HashMap<>();
        params.put("expected", exception);
        params.put("timeout", "5");

        String expected = Stream.of(expectedBody)
                .map(TestUtils::methodWrap)
                .map(c -> appendAnnotation(c, annotation, "timeout", "5"))
                .map(TestUtils::classWrap)
                .map(c -> appendImport(c, annotationImport, "Assertions"))
                .map(c -> appendImport(c, annotationImport, annotation))
                .map(TestUtils::prettyPrint)
                .collect(joining());

        String value = Stream.of(methodWrap(""))
                .map(c -> appendAnnotations(c, annotation, params))
                .map(TestUtils::classWrap)
                .map(c -> appendImport(c, annotationImport, annotation))
                .map(TestUtils::prettyPrint)
                .collect(joining());

        String result = tool.migrate(value);
        Assertions.assertEquals(expected, result);
    }
}
