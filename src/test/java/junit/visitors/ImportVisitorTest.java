package junit.visitors;

import junit.MigrationToolFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import api.service.MigrationTool;

import static junit.utils.TestUtils.*;
import static junit.utils.TestConstants.*;

public class ImportVisitorTest {

    private MigrationTool tool = MigrationToolFactory.getInstance();

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
        Assertions.assertEquals(prettyPrint(appendBeforeEmptyClass(expected)),
                prettyPrint(tool.migrate(appendBeforeEmptyClass(original))));
    }

    @Test
    public void asteriskImportTest() {
        appendBeforeClassAndAssert(
                appendImport("", NEW_IMPORT, ASSERTIONS_STRING + ".*"),
                appendImport("", OLD_IMPORT, ASSERT_STRING + ".*")
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
