import java.io.File;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.stream.Stream.of;

public class JUnitMigrationTool {

    public String migrate(String code) {
        return of(code)
                .map(this::migratePackage)
                .map(this::migrateAnnotations)
                .collect(joining());
    }

    private String migratePackage(String code) {
        return code.replaceAll("org.junit", "org.junit.jupiter.api");
    }

    private String migrateAnnotations(String code) {
        code = code
                .replaceAll("@Before", "@BeforeEach")
                .replaceAll("org.junit.jupiter.api.Before;", "org.junit.jupiter.api.BeforeEach;")
                .replaceAll("@After", "@AfterEach")
                .replaceAll("org.junit.jupiter.api.After;", "org.junit.jupiter.api.AfterEach;")
                .replaceAll("@BeforeClass", "@BeforeAll")
                .replaceAll("org.junit.jupiter.api.BeforeClass;", "org.junit.jupiter.api.BeforeAll;")
                .replaceAll("@AfterClass", "@AfterAll")
                .replaceAll("org.junit.jupiter.api.AfterClass;", "org.junit.jupiter.api.AfterAll;")
                .replaceAll("@Ignore", "@Disable")
                .replaceAll("org.junit.jupiter.api.Ignore", "org.junit.jupiter.api.Disable");


    }
}
