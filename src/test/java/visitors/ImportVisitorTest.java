package visitors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tool.JUnitMigrationTool;
import utils.TestUtils;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static utils.TestUtils.*;
import static utils.TestConstants.*;

public class ImportVisitorTest {

    private JUnitMigrationTool tool = new JUnitMigrationTool();

    @Test
    public void packageTest() {
        appendBeforeClassAndAssert(
                appendImport("", NEW_IMPORT, TEST_STRING),
                appendImport("", OLD_IMPORT, TEST_STRING)
        );
    }

    @Test
    public void assertImportTest() {
        appendBeforeClassAndAssert(
                appendImport("", NEW_IMPORT, ASSERTIONS_STRING),
                appendImport("", OLD_IMPORT, ASSERT_STRING)
        );
    }

    @Test
    public void assumeImportTest() {
        appendBeforeClassAndAssert(
                appendImport("", NEW_IMPORT, ASSUMPTIONS_STRING),
                appendImport("", OLD_IMPORT, ASSUME_STRING)
        );
    }


    private void appendBeforeClassAndAssert(String expected, String original) {
        Assertions.assertEquals(prettyPrint(appendBeforeEmptyClass(expected)), tool.migrate(appendBeforeEmptyClass(original)));
    }

    @Test
    public void asteriskImportTest() {
        appendBeforeClassAndAssert(
                appendImport("", NEW_IMPORT, ASSERTIONS_STRING + ".*"),
                appendImport("", OLD_IMPORT,  ASSERT_STRING + ".*")
        );
    }

    @Test
    public void staticImportTest() {
        appendBeforeClassAndAssert(
                appendStaticImport("", NEW_IMPORT, ASSERTIONS_STRING),
                appendStaticImport("", OLD_IMPORT, ASSERT_STRING)
        );
    }
}
