package counter.outgoing.reader;

import counter.outgoing.files.FileService;
import counter.outgoing.reader.filter.FileFilter;
import counter.outgoing.reader.tokenizer.Tokenizer;
import counter.outgoing.reader.word.FileWordSource;
import counter.outgoing.reader.word.WordSource;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirectoryContentReader implements ContentReader {

    private final Path directoryPath;
    private final Tokenizer tokenizer;
    private final FileFilter filter;
    private final FileService fileService;

    public DirectoryContentReader(Path directoryPath,
                                  Tokenizer tokenizer,
                                  FileFilter filter,
                                  FileService fileService) {
        this.directoryPath = directoryPath;
        this.tokenizer = tokenizer;
        this.filter = filter;
        this.fileService = fileService;
    }

    @Override
    public List<WordSource> contentSources() {
        try (Stream<Path> paths = fileService.listFiles(directoryPath)) {
            return paths
                    .filter(filter::includeFile)
                    .map(path -> new FileWordSource(path, tokenizer, fileService))
                    .collect(Collectors.toList());
        }
    }

}
