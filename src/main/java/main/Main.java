package main;

import api.entity.MigrationClassUnit;
import exception.FileProcessingException;
import impl.entity.MigrationClassUnitImpl;
import impl.MigrationPackageImpl;
import processor.FileProcessor;
import junit.JUnitMigrationTool;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new FileProcessingException("No path provided");
        }

        List<MigrationClassUnit> nodes = Arrays.asList(
                new MigrationClassUnitImpl("org.junit.Before", "org.junit.jupiter.api.BeforeEach"),
                new MigrationClassUnitImpl("org.junit.After", "org.junit.jupiter.api.AfterEach")
        );

        MigrationPackageImpl migrationPackage = new MigrationPackageImpl(nodes, new ArrayList<>());

        new FileProcessor(new JUnitMigrationTool(migrationPackage)).process(args[0]);
    }
}
