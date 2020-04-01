package junit.annotations;

import junit.MigrationToolFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import api.service.MigrationTool;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AnnotationSnapshotTestProcessor {

    private static final String JAVA = ".java";
    private static final String EXPECTED_DIRECTORY = "snapshots/annotations/expected";
    private static final String TEST_CASE_DIRECTORY = "snapshots/annotations/testcase";

    private MigrationTool tool = MigrationToolFactory.getInstance();

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
    void process(String testcaseName) throws Exception {
        Path testCase = Paths.get(TEST_CASE_DIRECTORY, testcaseName + JAVA);
        Path expected = Paths.get(EXPECTED_DIRECTORY, testcaseName + JAVA);
        assertEquals(Files.readString(expected), tool.migrate(Files.readString(testCase)));
    }
}
