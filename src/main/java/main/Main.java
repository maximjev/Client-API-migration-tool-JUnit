package main;

import api.entity.MigrationAnnotationUnit;
import api.entity.MigrationMethodUnit;
import api.service.MigrationChangeSet;
import exception.FileProcessingException;
import impl.MigrationChangeSetImpl;
import impl.entity.MigrationAnnotationUnitImpl;
import impl.entity.MigrationUnitWithClassImpl;
import impl.MigrationPackageImpl;
import processor.FileProcessor;
import junit.JUnitMigrationTool;
import api.entity.MigrationUnit;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new FileProcessingException("No path provided");
        }


        List<MigrationAnnotationUnit> nodes = Arrays.asList(
                new MigrationAnnotationUnitImpl("org.junit.Before", "org.junit.jupiter.api.BeforeEach"),
                new MigrationAnnotationUnitImpl("org.junit.After", "org.junit.jupiter.api.AfterEach")
        );

        MigrationChangeSet changeSet = new MigrationChangeSetImpl(new HashMap<>());

        MigrationPackageImpl migrationPackage = new MigrationPackageImpl(nodes, new ArrayList<MigrationMethodUnit>());
        new FileProcessor(new JUnitMigrationTool(migrationPackage, changeSet)).process(args[0]);
    }
}
