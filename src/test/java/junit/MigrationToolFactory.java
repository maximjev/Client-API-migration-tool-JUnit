package junit;

import api.entity.MigrationAnnotationUnit;
import api.entity.MigrationMethodUnit;
import api.service.MigrationChangeSet;
import impl.MigrationChangeSetImpl;
import impl.entity.MigrationAnnotationUnitImpl;
import impl.entity.MigrationMethodUnitImpl;
import impl.MigrationPackageImpl;
import api.service.MigrationTool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MigrationToolFactory {

    public static MigrationTool getInstance() {
        List<MigrationAnnotationUnit> annotations = Arrays.asList(
                new MigrationAnnotationUnitImpl("org.junit.Before", "org.junit.jupiter.api.BeforeEach"),
                new MigrationAnnotationUnitImpl("org.junit.After", "org.junit.jupiter.api.AfterEach"),
                new MigrationAnnotationUnitImpl("org.junit.BeforeClass", "org.junit.jupiter.api.BeforeAll"),
                new MigrationAnnotationUnitImpl("org.junit.AfterClass", "org.junit.jupiter.api.AfterAll"),
                new MigrationAnnotationUnitImpl("org.junit.Ignore", "org.junit.jupiter.api.Disabled")
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
                        "assertAll")
        );

        MigrationChangeSet changeSet = new MigrationChangeSetImpl(new HashMap<>());

        return new JUnitMigrationTool(new MigrationPackageImpl(annotations, methods), changeSet);
    }
}
