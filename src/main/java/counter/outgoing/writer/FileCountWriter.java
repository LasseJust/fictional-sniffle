package counter.outgoing.writer;

import counter.domain.WordCount;
import counter.outgoing.files.FileService;
import counter.outgoing.writer.grouping.GroupingStrategy;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

public class FileCountWriter implements CountWriter {

    private final Path outputDir;
    private final GroupingStrategy groupingStrategy;
    private final FileService fileService;

    public FileCountWriter(Path outputDir,
                           GroupingStrategy groupingStrategy,
                           FileService fileService) {
        this.outputDir = outputDir;
        this.groupingStrategy = groupingStrategy;
        this.fileService = fileService;
    }

    @Override
    public void writeCounts(WordCount wordCounts) {
        fileService.createDir(outputDir);

        getCountsGroupedByFiles(wordCounts)
                .entrySet()
                .parallelStream()
                .forEach(fileEntry -> writeToFile(fileEntry.getKey(), fileEntry.getValue()));
    }

    private Map<String, Map<String, Long>> getCountsGroupedByFiles(WordCount wordCounts) {
        return wordCounts.wordCounts().entrySet()
                .stream()
                .collect(Collectors.groupingBy(
                        entry -> groupingStrategy.group(entry.getKey()),
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
                ));
    }

    private void writeToFile(String fileName, Map<String, Long> wordCounts) {
        Path filePath = outputDir.resolve(fileName);
        try (BufferedWriter writer = fileService.newBufferedWriter(filePath)) {
            for (Map.Entry<String, Long> wordEntry : wordCounts.entrySet()) {
                String word = wordEntry.getKey();
                Long count = wordEntry.getValue();
                writer.write(String.format("%s %d", word, count));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new DataWriteException(e);
        }
    }

}
