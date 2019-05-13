import com.github.javaparser.JavaParser;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MigrationToolTest {

    private JUnitMigrationTool tool = new JUnitMigrationTool();

    private static String CLASS_TEMPLATE = "public class Foo { %s }";
    private static String METHOD_TEMPLATE = "public void bar() { %s }";

    private static String prettyPrint(String code) {
        return JavaParser.parse(code).toString();
    }

    private static String classWrap(String code) {
        return prettyPrint(format(CLASS_TEMPLATE, code));
    }

    private static String classMethodWrap(String code) {
        return classWrap(format(METHOD_TEMPLATE, code));
    }

    private static String appendBeforeEmptyClass(String code) {
        return prettyPrint(code + format(CLASS_TEMPLATE, ""));
    }

    private static String appendBeforeEmptyMethod(String code) {
        return code + format(METHOD_TEMPLATE, "");
    }


    private String constructClassForMethodAnnotation(String annotation, String annotationImport) {
        return prettyPrint(annotationImport + annotation + "; " +
                classWrap(appendBeforeEmptyMethod("@" + annotation + " ")));
    }

    private void appendBeforeClassAndAssert(String original, String expected) {
        assertEquals(tool.migrate(appendBeforeEmptyClass(original)), appendBeforeEmptyClass(expected));
    }

    @Test
    public void packageTest() {
        String original = "import org.junit.Test;";
        String expected = "import org.junit.jupiter.api.Test;";

        appendBeforeClassAndAssert(original, expected);
    }


    @Test
    public void annotationMigrationTest() {
        Map<String, String> annotations = new HashMap<>();

        annotations.put("Before", "BeforeEach");
        annotations.put("After", "AfterEach");
        annotations.put("BeforeClass", "BeforeEach");
        annotations.put("AfterClass", "AfterEach");
        annotations.put("Ignore", "Disabled");

        annotations.forEach(this::compareAnnotationsAndAssert);
    }

    private void compareAnnotationsAndAssert(String oldAnnotation, String newAnnotation) {
        String oldImport = "import org.junit.";
        String newImport = "import org.junit.jupiter.api.";
        assertEquals(tool.migrate(constructClassForMethodAnnotation(oldAnnotation, oldImport)),
                constructClassForMethodAnnotation(newAnnotation, newImport));
    }


}
