package junit;

import api.entity.MigrationArgumentUnit;
import api.entity.MigrationUnit;
import impl.entity.MigrationAnnotationUnit;
import impl.entity.MigrationMethodArgumentUnit;
import impl.entity.MigrationMethodUnit;
import impl.MigrationPackageImpl;
import api.service.MigrationTool;
import impl.tool.MigrationToolImpl;

import java.util.Arrays;
import java.util.List;



public class MigrationToolFactory {

    public static MigrationTool getInstance() {

        List<MigrationArgumentUnit> arguments = Arrays.asList(
                new MigrationMethodArgumentUnit(1, 3),
                new MigrationMethodArgumentUnit(2, 1),
                new MigrationMethodArgumentUnit(3, 2)
        );
        List<MigrationUnit> migrationUnits = Arrays.asList(
                new MigrationAnnotationUnit("org.junit.Before", "org.junit.jupiter.api.BeforeEach"),
                new MigrationAnnotationUnit("org.junit.After", "org.junit.jupiter.api.AfterEach"),
                new MigrationAnnotationUnit("org.junit.BeforeClass", "org.junit.jupiter.api.BeforeAll"),
                new MigrationAnnotationUnit("org.junit.AfterClass", "org.junit.jupiter.api.AfterAll"),
                new MigrationAnnotationUnit("org.junit.Ignore", "org.junit.jupiter.api.Disabled"),
                new MigrationMethodUnit("org.junit.Assert.assertEquals", "org.junit.jupiter.api.Assertions.assertEquals", arguments),
                new MigrationMethodUnit("org.junit.Assert.assertAll", "org.junit.jupiter.api.Assertions.assertAll", arguments),
                new MigrationMethodUnit("org.junit.Assume.assumeTrue", "org.junit.jupiter.api.Assumptions.assumeTrue", arguments)
        );

        return new MigrationToolImpl(new MigrationPackageImpl(migrationUnits));
    }
}
