import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    public static void main(String args[]) throws IOException {
        if (args.length == 0) {
            throw new ParsingException("No path provided");
        }

        FileParser parser = new FileParser(new JUnitMigrationTool());

        parser.navigateDir(Paths.get(args[0]));
    }


}
