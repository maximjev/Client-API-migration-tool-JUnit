package junit;

import api.entity.MigrationClassUnit;
import api.entity.MigrationMethodUnit;
import impl.entity.MigrationClassUnitImpl;
import impl.entity.MigrationMethodUnitImpl;
import impl.MigrationPackageImpl;
import api.service.MigrationTool;
import org.junit.jupiter.api.Assumptions;

import java.util.Arrays;
import java.util.List;

public class MigrationToolFactory {

    public static MigrationTool getInstance() {
        List<MigrationClassUnit> annotations = Arrays.asList(
                new MigrationClassUnitImpl("org.junit.Before", "org.junit.jupiter.api.BeforeEach"),
                new MigrationClassUnitImpl("org.junit.After", "org.junit.jupiter.api.AfterEach"),
                new MigrationClassUnitImpl("org.junit.BeforeClass", "org.junit.jupiter.api.BeforeAll"),
                new MigrationClassUnitImpl("org.junit.AfterClass", "org.junit.jupiter.api.AfterAll"),
                new MigrationClassUnitImpl("org.junit.Ignore", "org.junit.jupiter.api.Disabled")
        );

        List<MigrationMethodUnit> methods = Arrays.asList(
                new MigrationMethodUnitImpl(
                        "org.junit.Assert",
                        "org.junit.jupiter.api.Assertions",
                        "assertEquals",
                        "assertEquals"),
                new MigrationMethodUnitImpl(
                        "org.junit.Assert",
                        "org.junit.jupiter.api.Assertions",
                        "assertAll",
                        "assertAll"),
                new MigrationMethodUnitImpl(
                        "org.junit.Assume",
                        "org.junit.jupiter.api.Assumptions",
                        "assumeTrue",
                        "assumeTrue")
        );



        return new JUnitMigrationTool(new MigrationPackageImpl(annotations, methods));
    }
}
