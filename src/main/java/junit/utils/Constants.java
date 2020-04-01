package junit.utils;

import java.util.Map;

public class Constants {
    public static final Map<String, String> ANNOTATION_MAP = Map.of(
            "Before", "BeforeEach",
            "After", "AfterEach",
            "BeforeClass", "BeforeAll",
            "AfterClass", "AfterAll",
            "Ignore", "Disabled");

    public static final String NEW_IMPORT = "org.junit.jupiter.api";
    public static final String OLD_IMPORT = "org.junit";
    public static final String ASSERT_STRING = "Assert";
    public static final String ASSERTIONS_STRING = "Assertions";
    public static final String ASSUME_STRING = "Assume";
    public static final String ASSUMPTIONS_STRING = "Assumptions";
    public static final String TEST_STRING = "Test";
    public static final String EXPECTED_STRING = "expected";
}
