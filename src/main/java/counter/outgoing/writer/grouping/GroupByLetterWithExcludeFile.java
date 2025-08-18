package counter.outgoing.writer.grouping;

import counter.outgoing.files.FileService;
import counter.outgoing.reader.tokenizer.Tokenizer;

import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupByLetterWithExcludeFile implements GroupingStrategy {

    private final Set<String> excludedWords;
    private final String excludeGroupName;

    public GroupByLetterWithExcludeFile(Set<String> excludedWords,
                                        String excludeGroupName) {
        this.excludedWords = excludedWords;
        this.excludeGroupName = excludeGroupName;
    }

    public static GroupByLetterWithExcludeFile fromFile(FileService fileService,
                                                        Path excludeFile,
                                                        String excludeGroupName,
                                                        Tokenizer tokenizer) {
        try (Stream<String> lines = fileService.readLines(excludeFile)) {
            Set<String> excludedWords = lines
                    .flatMap(tokenizer::tokenize)
                    .collect(Collectors.toSet());
            return new GroupByLetterWithExcludeFile(excludedWords, excludeGroupName);
        }
    }

    @Override
    public String group(String word) {
        if (excludedWords.contains(word)) {
            return excludeGroupName;
        }
        char groupLetter = word.charAt(0);
        return String.format("FILE_%C.txt", groupLetter);
    }
}
