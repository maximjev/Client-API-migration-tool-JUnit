import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImportAndAnnotationVisitorTest {

    private JUnitMigrationTool tool = new JUnitMigrationTool();

    @Test
    public void packageTest() {
        String original = "import org.junit.Test;";
        String expected = "import org.junit.jupiter.api.Test;";

        appendBeforeClassAndAssert(original, expected);
    }

    private void appendBeforeClassAndAssert(String original, String expected) {
        assertEquals(tool.migrate(TestUtils.appendBeforeEmptyClass(original)), TestUtils.appendBeforeEmptyClass(expected));
    }

    @Test
    public void asteriskImportTest() {
        String original = "import org.junit.*;";
        String expected = "import org.junit.jupiter.api.*;";

        appendBeforeClassAndAssert(original, expected);
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
        assertEquals(tool.migrate(TestUtils.constructClassForMethodAnnotation(oldAnnotation, oldImport)),
                TestUtils.constructClassForMethodAnnotation(newAnnotation, newImport));
    }
}
