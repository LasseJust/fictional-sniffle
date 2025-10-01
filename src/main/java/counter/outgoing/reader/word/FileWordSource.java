package counter.outgoing.reader.word;

import counter.domain.model.WordSource;
import counter.outgoing.shared.file.FileService;
import counter.domain.tokenizer.Tokenizer;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FileWordSource implements WordSource {

    private final Path dataFilePath;
    private final Tokenizer tokenizer;
    private final FileService fileService;

    public FileWordSource(Path dataFilePath,
                          Tokenizer tokenizer,
                          FileService fileService) {
        this.dataFilePath = dataFilePath;
        this.tokenizer = tokenizer;
        this.fileService = fileService;
    }

    @Override
    public Stream<String> words() {
        return fileService.readLines(dataFilePath)
                .flatMap(tokenizer::tokenize)
                .filter(Predicate.not(String::isBlank));
    }
}
