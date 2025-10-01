package counter.ingoing.cli;

import counter.app.AppConfig;
import counter.domain.WordCounterService;
import counter.domain.exception.DataReadException;
import counter.domain.exception.DataWriteException;

import java.nio.file.Path;

public class CommandLineRunner {

    private static final String EXCLUDE_OUTPUT_FILENAME = "EXCLUDED_WORDS_COUNTS.txt";

    public static void main(String[] args) {
        try {
            CmdLineArgs parsedArgs = parseArgs(args);
            WordCounterService wordCounterService = AppConfig.wireCounterService(parsedArgs);
            wordCounterService.countWords();
            System.out.println("Completed successfully");
        } catch (IllegalArgumentException | DataReadException | DataWriteException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private static CmdLineArgs parseArgs(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Invalid number of arguments. Usage: java -jar <jar-path> <input-path> <output-path> <exclude-file-path>");
        }
        var inputPath = Path.of(args[0]);
        var outputPath = Path.of(args[1]);
        var excludeFile = Path.of(args[2]);
        return new CmdLineArgs(inputPath, outputPath, excludeFile, EXCLUDE_OUTPUT_FILENAME);
    }
}
