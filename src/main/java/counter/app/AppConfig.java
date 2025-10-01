package counter.app;

import counter.domain.WordCounterService;
import counter.domain.port.ContentReader;
import counter.domain.port.CountWriter;
import counter.ingoing.cli.CmdLineArgs;
import counter.outgoing.shared.file.DefaultFileSystemFileService;
import counter.outgoing.shared.file.FileService;
import counter.outgoing.reader.DirectoryContentReader;
import counter.outgoing.reader.filter.ExcludeFileFilter;
import counter.outgoing.reader.filter.FileFilter;
import counter.domain.tokenizer.LowerCaseTrimmingTokenizer;
import counter.domain.tokenizer.Tokenizer;
import counter.outgoing.writer.FileCountWriter;
import counter.outgoing.writer.grouping.GroupByLetterWithExcludeFile;
import counter.outgoing.writer.grouping.GroupingStrategy;

import java.util.Set;

public class AppConfig {

    // Manual "auto"-wiring
    public static WordCounterService wireCounterService(CmdLineArgs args) {
        // Base/shared
        FileService fileService = new DefaultFileSystemFileService();

        // Reader
        Tokenizer tokenizer = new LowerCaseTrimmingTokenizer();
        FileFilter filter = new ExcludeFileFilter(Set.of(args.excludeFilePath().getFileName()), fileService);
        ContentReader contentReader = new DirectoryContentReader(args.inputPath(), tokenizer, filter, fileService);

        // Writer
        GroupingStrategy groupingStrategy = GroupByLetterWithExcludeFile.fromFile(
                fileService, args.excludeFilePath(), args.excludeOutputFilename(), tokenizer);
        CountWriter countWriter = new FileCountWriter(args.outputPath(), groupingStrategy, fileService);

        // Processor
        return new WordCounterService(contentReader, countWriter);
    }

}
