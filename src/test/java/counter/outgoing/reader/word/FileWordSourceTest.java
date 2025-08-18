package counter.outgoing.reader.word;

import counter.outgoing.files.FileService;
import counter.outgoing.reader.tokenizer.LowerCaseTrimmingTokenizer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.util.stream.Stream;


@ExtendWith(MockitoExtension.class)
class FileWordSourceTest {

    @Mock
    FileService fileService;

    FileWordSource fileWordSource;

    @BeforeEach
    void setUp() {
        fileWordSource = new FileWordSource(Path.of("file"), new LowerCaseTrimmingTokenizer(), fileService);
        Mockito.when(fileService.readLines(Mockito.any()))
                .thenReturn(Stream.of("first line", "second line"));
    }

    @Test
    void words_withWords_flattened() {
        Stream<String> words = fileWordSource.words();

        Assertions.assertThat(words).containsExactlyInAnyOrder("first", "line", "second", "line");
    }
}
