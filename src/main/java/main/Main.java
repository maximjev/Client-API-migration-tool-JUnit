package main;

import api.entity.MigrationUnit;
import exception.FileProcessingException;
import impl.entity.MigrationAnnotationUnit;
import impl.entity.MigrationUnitImpl;
import impl.MigrationPackageImpl;
import processor.FileProcessor;
import junit.JUnitMigrationTool;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new FileProcessingException("No path provided");
        }

        List<MigrationUnit> nodes = Arrays.asList(
                new MigrationAnnotationUnit("org.junit.Before", "org.junit.jupiter.api.BeforeEach"),
                new MigrationAnnotationUnit("org.junit.After", "org.junit.jupiter.api.AfterEach")
        );

        MigrationPackageImpl migrationPackage = new MigrationPackageImpl(nodes);

        new FileProcessor(new JUnitMigrationTool(migrationPackage)).process(args[0]);
    }
}
