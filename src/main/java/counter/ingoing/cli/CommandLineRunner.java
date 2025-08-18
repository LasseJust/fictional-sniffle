package counter.ingoing.cli;

import counter.domain.WordCounterApp;
import counter.domain.WordCounterService;
import counter.outgoing.files.DefaultFileSystemFileService;
import counter.outgoing.files.FileService;
import counter.outgoing.reader.ContentReader;
import counter.outgoing.reader.DataReadException;
import counter.outgoing.reader.DirectoryContentReader;
import counter.outgoing.reader.filter.ExcludeFileFilter;
import counter.outgoing.reader.filter.FileFilter;
import counter.outgoing.reader.tokenizer.LowerCaseTrimmingTokenizer;
import counter.outgoing.reader.tokenizer.Tokenizer;
import counter.outgoing.writer.CountWriter;
import counter.outgoing.writer.DataWriteException;
import counter.outgoing.writer.FileCountWriter;
import counter.outgoing.writer.grouping.GroupByLetterWithExcludeFile;
import counter.outgoing.writer.grouping.GroupingStrategy;

import java.nio.file.Path;
import java.util.Set;

public class CommandLineRunner {

    private static final String EXCLUDE_OUTPUT_FILENAME = "EXCLUDED_WORDS_COUNTS.txt";

    public static void main(String[] args) {
        try {
            CmdLineArgs parsedArgs = parseArgs(args);
            WordCounterApp wordCounterApp = wireApp(parsedArgs);
            wordCounterApp.countWords();
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
        return new CmdLineArgs(inputPath, outputPath, excludeFile);
    }

    private static WordCounterApp wireApp(CmdLineArgs args) {
        // Base/shared
        FileService fileService = new DefaultFileSystemFileService();

        // Reader
        Tokenizer tokenizer = new LowerCaseTrimmingTokenizer();
        FileFilter filter = new ExcludeFileFilter(Set.of(args.excludeFilePath().getFileName()), fileService);
        ContentReader contentReader = new DirectoryContentReader(args.inputPath(), tokenizer, filter, fileService);

        // Writer
        GroupingStrategy groupingStrategy = GroupByLetterWithExcludeFile.fromFile(
                fileService, args.excludeFilePath(), EXCLUDE_OUTPUT_FILENAME, tokenizer);
        CountWriter countWriter = new FileCountWriter(args.outputPath(), groupingStrategy, fileService);

        // Processor
        WordCounterService wordCounterService = new WordCounterService();

        return new WordCounterApp(contentReader, countWriter, wordCounterService);
    }
}
