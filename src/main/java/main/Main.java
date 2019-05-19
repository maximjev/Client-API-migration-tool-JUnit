package main;

import exception.ParsingException;
import parser.FileParser;
import tool.JUnitMigrationTool;

import java.nio.file.Paths;

public class Main {

    public static void main(String args[]) {
        if (args.length == 0) {
            throw new ParsingException("No path provided");
        }

        FileParser parser = new FileParser(new JUnitMigrationTool());

        parser.navigateDir(Paths.get(args[0]));
    }
}
