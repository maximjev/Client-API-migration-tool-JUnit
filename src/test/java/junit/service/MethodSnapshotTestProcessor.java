package junit.service;

import junit.MigrationToolFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import api.service.MigrationTool;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MethodSnapshotTestProcessor {

    private static final String JAVA = ".java";
    private static final String EXPECTED_DIRECTORY = "snapshots/methods/expected";
    private static final String TEST_CASE_DIRECTORY = "snapshots/methods/testcase";

    private MigrationTool tool = MigrationToolFactory.getInstance();

    @ParameterizedTest(name = "{0} test")
    @ValueSource(strings = {
            "AssertEquals",
            "AssertEqualsStatic",
            "AssertEqualsStaticAsterisk",
            "AssumeTrue",
            "AssumeTrueStatic",
            "AssumeTrueStaticAsterisk",
            "AssertEqualsText"
    })
    void process(String testcaseName) throws Exception {
        Path testCase = Paths.get(TEST_CASE_DIRECTORY, testcaseName + JAVA);
        Path expected = Paths.get(EXPECTED_DIRECTORY, testcaseName + JAVA);
        assertEquals(Files.readString(expected), tool.migrate(Files.readString(testCase)));
    }
}
