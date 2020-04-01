package junit.visitors;

import junit.MigrationToolFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import junit.utils.TestUtils;
import api.service.MigrationTool;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static junit.utils.TestConstants.NEW_IMPORT;
import static junit.utils.TestConstants.OLD_IMPORT;
import static junit.utils.TestUtils.constructClassForMethodAnnotation;
import static junit.utils.TestUtils.prettyPrint;

public class AnnotationVisitorTest {

    private MigrationTool tool = MigrationToolFactory.getInstance();

    @Test
    public void annotationMigrationTest() {
        Map<String, String> annotations = Map.of(
                "Before", "BeforeEach",
                "After", "AfterEach",
                "BeforeClass", "BeforeAll",
                "AfterClass", "AfterAll",
                "Ignore", "Disabled");

        annotations.forEach(this::compareAnnotationsAndAssert);
    }

    private void compareAnnotationsAndAssert(String oldAnnotation, String newAnnotation) {
        String expected = Stream.of(NEW_IMPORT)
                .map(i -> constructClassForMethodAnnotation(newAnnotation, i))
                .map(TestUtils::prettyPrint)
                .collect(joining());

        String value = Stream.of(OLD_IMPORT)
                .map(i -> constructClassForMethodAnnotation(oldAnnotation, i))
                .map(TestUtils::prettyPrint)
                .collect(joining());

        Assertions.assertEquals(expected, prettyPrint(tool.migrate(value)));
    }
}
