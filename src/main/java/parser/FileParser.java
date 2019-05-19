package parser;

import exception.ParsingException;
import tool.JUnitMigrationTool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileParser {

    private JUnitMigrationTool tool;

    public FileParser(JUnitMigrationTool tool) {
        this.tool = tool;
    }

    public void navigateDir(Path path) {
        File file = new File(path.toUri());
        if (!file.exists()) {
            throw new ParsingException("Invalid path");
        }

        if (file.isFile()) {
            parse(path);
        } else {
            try {
                Stream<Path> pathStream = Files.walk(path);
                pathStream
                        .filter(p -> p.toString().endsWith(".java") && p.toFile().isFile())
                        .forEach(this::parse);
                pathStream.close();
            } catch (IOException ex) {
                throw new ParsingException("Error while opening files", ex);
            }
        }
    }

    private void parse(Path path) {
        try {
            String originalCode = Files.readString(path);
            String modifiedCode = tool.migrate(originalCode);
            if (!originalCode.equals(modifiedCode)) {
                System.out.println("Modifying file: " + path.getFileName());
                Files.write(path, modifiedCode.getBytes());
            } else {
                System.out.println("No changes applied to: " + path.getFileName());
            }
        } catch (IOException ex) {
            throw new ParsingException("Error while reading file", ex);
        }
    }
}
