package junit.service;

import impl.tool.MigrationToolImpl;
import junit.JUnitMigrationPackage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import api.service.MigrationTool;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MigrationSnapshotTest {

    private static final String JAVA = ".java";

    private static final String ASSERTIONS_EXPECTED_DIR = "snapshots/assertions/expected";
    private static final String ASSERTIONS_ACTUAL_DIR = "snapshots/assertions/actual";

    private static final String ASSUMPTIONS_EXPECTED_DIR = "snapshots/assumptions/expected";
    private static final String ASSUMPTIONS_ACTUAL_DIR = "snapshots/assumptions/actual";

    private static final String ANNOTATIONS_EXPECTED_DIR = "snapshots/annotations/expected";
    private static final String ANNOTATIONS_ACTUAL_DIR = "snapshots/annotations/actual";

    private static final String CUSTOM_EXPECTED_DIR = "snapshots/custom/expected";
    private static final String CUSTOM_ACTUAL_DIR = "snapshots/custom/actual";

    private MigrationTool tool = new MigrationToolImpl(JUnitMigrationPackage.getInstance());

    @ParameterizedTest(name = "{0} test")
    @ValueSource(strings = {
            "AssertEquals",
            "AssertEqualsStatic",
            "AssertEqualsStaticAsterisk",
            "AssertEqualsText"
    })
    void assertions(String testcaseName) throws Exception {
        Path actual = Paths.get(ASSERTIONS_ACTUAL_DIR, testcaseName + JAVA);
        Path expected = Paths.get(ASSERTIONS_EXPECTED_DIR, testcaseName + JAVA);
        assertEquals(Files.readString(expected), tool.migrate(Files.readString(actual)));
    }

    @ParameterizedTest(name = "{0} test")
    @ValueSource(strings = {
            "AssumeTrue",
            "AssumeTrueStatic",
            "AssumeTrueStaticAsterisk"
    })
    void assumptions(String testcaseName) throws Exception {
        Path actual = Paths.get(ASSUMPTIONS_ACTUAL_DIR, testcaseName + JAVA);
        Path expected = Paths.get(ASSUMPTIONS_EXPECTED_DIR, testcaseName + JAVA);
        assertEquals(Files.readString(expected), tool.migrate(Files.readString(actual)));
    }

    @ParameterizedTest(name = "{0} test")
    @ValueSource(strings = {
            "BeforeEach",
            "AfterEach",
            "BeforeAll",
            "AfterAll",
            "DisabledTest",
            "DisabledClass",
            "NotRelevant",
            "BeforeAndAfterAll",
            "BeforeAndAfterEach",
            "BeforeEachAsterisk",
            "BeforeAndAfterEachAsterisk"
    })
    void annotations(String testcaseName) throws Exception {
        Path actual = Paths.get(ANNOTATIONS_ACTUAL_DIR, testcaseName + JAVA);
        Path expected = Paths.get(ANNOTATIONS_EXPECTED_DIR, testcaseName + JAVA);
        assertEquals(Files.readString(expected), tool.migrate(Files.readString(actual)));
    }

    @ParameterizedTest(name = "{0} test")
    @ValueSource(strings = {
            "AssertThrows",
            "AssertThrowsWithImport"
    })
    void custom(String testcaseName) throws Exception {
        Path actual = Paths.get(CUSTOM_ACTUAL_DIR, testcaseName + JAVA);
        Path expected = Paths.get(CUSTOM_EXPECTED_DIR, testcaseName + JAVA);
        assertEquals(Files.readString(expected), tool.migrate(Files.readString(actual)));
    }
}
