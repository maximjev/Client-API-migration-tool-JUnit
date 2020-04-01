package processor;

import exception.FileProcessingException;
import api.service.MigrationTool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileProcessor {

    private MigrationTool tool;

    public FileProcessor(MigrationTool tool) {
        this.tool = tool;
    }

    public void process(String directory) {
        Path path = Paths.get(directory);
        File file = new File(path.toUri());
        if (!file.exists()) {
            throw new FileProcessingException("Invalid path");
        }

        if (file.isFile()) {
            processFile(path);
        } else {
            try (Stream<Path> pathStream = Files.walk(path)) {
                pathStream
                        .filter(p -> p.toString().endsWith(".java"))
                        .filter(p -> p.toFile().isFile())
                        .forEach(this::processFile);
            } catch (IOException ex) {
                throw new FileProcessingException("Error while opening files", ex);
            }
        }
    }

    private void processFile(Path path) {
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
            throw new FileProcessingException("Error while reading file", ex);
        }
    }
}
