package junit;

import api.entity.MigrationUnit;
import impl.entity.MigrationAnnotationUnit;
import impl.entity.MigrationMethodUnit;
import impl.MigrationPackageImpl;
import api.service.MigrationTool;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class MigrationToolFactory {

    public static MigrationTool getInstance() {
        List<MigrationUnit> annotations = Arrays.asList(
                new MigrationAnnotationUnit("org.junit.Before", "org.junit.jupiter.api.BeforeEach"),
                new MigrationAnnotationUnit("org.junit.After", "org.junit.jupiter.api.AfterEach"),
                new MigrationAnnotationUnit("org.junit.BeforeClass", "org.junit.jupiter.api.BeforeAll"),
                new MigrationAnnotationUnit("org.junit.AfterClass", "org.junit.jupiter.api.AfterAll"),
                new MigrationAnnotationUnit("org.junit.Ignore", "org.junit.jupiter.api.Disabled"),
                new MigrationMethodUnit("org.junit.Assert.assertEquals", "org.junit.jupiter.api.Assertions.assertEquals"),
                new MigrationMethodUnit("org.junit.Assert.assertAll", "org.junit.jupiter.api.Assertions.assertAll"),
                new MigrationMethodUnit("org.junit.Assume.assumeTrue", "org.junit.jupiter.api.Assumptions.assumeTrue")
        );

        return new JUnitMigrationTool(new MigrationPackageImpl(annotations));
    }
}
