package counter.outgoing.reader;

import counter.outgoing.shared.file.FileService;
import counter.outgoing.reader.filter.FileFilter;
import counter.domain.tokenizer.LowerCaseTrimmingTokenizer;
import counter.domain.model.WordSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class DirectoryContentReaderTest {

    @Mock
    FileFilter filter;
    @Mock
    FileService fileService;

    DirectoryContentReader directoryContentReader;

    @BeforeEach
    void setUp() {
        directoryContentReader = new DirectoryContentReader(
                Path.of("dir"),
                new LowerCaseTrimmingTokenizer(),
                filter,
                fileService);

        Mockito.when(fileService.listFiles(Mockito.any()))
                .thenReturn(Stream.of(Path.of("f1"), Path.of("f2")));

    }

    @Test
    void contentSources_noFilter() {
        Mockito.when(filter.includeFile(Mockito.any())).thenReturn(true);

        List<? extends WordSource> wordSources = directoryContentReader.contentSources();

        Assertions.assertThat(wordSources).hasSize(2);
    }

    @Test
    void contentSources_withFilter() {
        Mockito.when(filter.includeFile(Mockito.any())).thenReturn(false);

        List<? extends WordSource> wordSources = directoryContentReader.contentSources();

        Assertions.assertThat(wordSources).hasSize(0);
    }
}
