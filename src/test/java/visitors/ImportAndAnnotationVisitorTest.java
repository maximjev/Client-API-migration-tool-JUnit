package visitors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tool.JUnitMigrationTool;
import utils.TestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestUtils.*;

public class ImportAndAnnotationVisitorTest {

    private JUnitMigrationTool tool = new JUnitMigrationTool();

    @Test
    public void packageTest() {
        String original = "import org.junit.Test;";
        String expected = "import org.junit.jupiter.api.Test;";

        appendBeforeClassAndAssert(expected, original);
    }

    private void appendBeforeClassAndAssert(String expected, String original) {
        Assertions.assertEquals(prettyPrint(appendBeforeEmptyClass(expected)), tool.migrate(appendBeforeEmptyClass(original)));
    }

    @Test
    public void asteriskImportTest() {
        String original = "import org.junit.*;";
        String expected = "import org.junit.jupiter.api.*;";

        appendBeforeClassAndAssert(expected, original);
    }


    @Test
    public void annotationMigrationTest() {
        Map<String, String> annotations = new HashMap<>();

        annotations.put("Before", "BeforeEach");
        annotations.put("After", "AfterEach");
        annotations.put("BeforeClass", "BeforeAll");
        annotations.put("AfterClass", "AfterAll");
        annotations.put("Ignore", "Disabled");

        annotations.forEach(this::compareAnnotationsAndAssert);
    }

    private void compareAnnotationsAndAssert(String oldAnnotation, String newAnnotation) {
        String oldImport = "import org.junit.";
        String newImport = "import org.junit.jupiter.api.";

        String expected = Stream.of(newImport)
                .map(i -> constructClassForMethodAnnotation(newAnnotation, i))
                .map(TestUtils::prettyPrint)
                .collect(joining());

        String value = Stream.of(oldImport)
                .map(i -> constructClassForMethodAnnotation(oldAnnotation, i))
                .map(TestUtils::prettyPrint)
                .collect(joining());

        Assertions.assertEquals(expected, tool.migrate(value));
    }
}
