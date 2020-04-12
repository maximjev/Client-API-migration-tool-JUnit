package main;

import api.service.MigrationPackage;
import exception.FileProcessingException;
import impl.tool.MigrationToolImpl;
import junit.JUnitMigrationPackage;
import processor.FileProcessor;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new FileProcessingException("No path provided");
        }

        MigrationPackage migrationPackage = JUnitMigrationPackage.getInstance();
        new FileProcessor(new MigrationToolImpl(migrationPackage)).process(args[0]);
    }
}
