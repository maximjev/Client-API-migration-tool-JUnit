package main;

import api.entity.MigrationUnit;
import exception.FileProcessingException;
import impl.entity.MigrationAnnotationUnit;
import impl.MigrationPackageImpl;
import impl.tool.MigrationToolImpl;
import processor.FileProcessor;

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

        new FileProcessor(new MigrationToolImpl(migrationPackage)).process(args[0]);
    }
}
