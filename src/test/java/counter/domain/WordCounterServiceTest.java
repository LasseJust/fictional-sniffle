package counter.domain;

import counter.domain.port.ContentReader;
import counter.domain.port.CountWriter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class WordCounterServiceTest {

    @Mock
    ContentReader contentReader;
    @Mock
    CountWriter countWriter;

    WordCounterService wordCounterService;

    @BeforeEach
    void setUp() {
        wordCounterService = new WordCounterService(contentReader, countWriter);
        Mockito.when(contentReader.contentSources()).thenReturn(List.of(() -> Stream.of("hej")));
    }

    @Test
    void countWords() {
        Assertions.assertThatNoException()
                .isThrownBy(() -> wordCounterService.countWords());

        Mockito.verify(contentReader).contentSources();
        Mockito.verify(countWriter).writeCounts(Mockito.any());
    }
}
