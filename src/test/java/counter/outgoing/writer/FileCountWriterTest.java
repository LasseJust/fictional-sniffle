package counter.outgoing.writer;

import counter.domain.model.WordCount;
import counter.outgoing.shared.file.FileService;
import counter.outgoing.writer.grouping.GroupByLetterWithExcludeFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;


@ExtendWith(MockitoExtension.class)
class FileCountWriterTest {

    @Mock
    FileService fileService;
    @Mock
    BufferedWriter bufferedWriter;

    FileCountWriter fileCountWriter;

    @Captor
    ArgumentCaptor<Path> outputPathsCaptor;
    @Captor
    ArgumentCaptor<String> outputLinesCaptor;

    @BeforeEach
    void setUp() throws IOException {
        fileCountWriter = new FileCountWriter(
                Path.of("dir"),
                new GroupByLetterWithExcludeFile(Set.of("dig"), "ignored_words.txt"),
                fileService);

        Mockito.when(fileService.newBufferedWriter(outputPathsCaptor.capture())).thenReturn(bufferedWriter);
        Mockito.doNothing().when(bufferedWriter).write(outputLinesCaptor.capture());
    }

    @Test
    void writeCounts() {
        WordCount wordCounts = new WordCount(Map.of("hej", 1L, "med", 2L, "dig", 3L));

        fileCountWriter.writeCounts(wordCounts);

        List<Path> outputPaths = outputPathsCaptor.getAllValues();
        Assertions.assertThat(outputPaths)
                .containsExactlyInAnyOrder(
                        Path.of("dir", "FILE_H.txt"),
                        Path.of("dir", "FILE_M.txt"),
                        Path.of("dir", "ignored_words.txt"));

        List<String> outputLines = outputLinesCaptor.getAllValues();
        Assertions.assertThat(outputLines)
                .containsExactlyInAnyOrder(
                        "hej 1",
                        "med 2",
                        "dig 3");
    }
}