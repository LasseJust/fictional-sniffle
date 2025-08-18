package counter.domain;

import counter.outgoing.reader.ContentReader;
import counter.outgoing.writer.CountWriter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class WordCounterAppTest {

    @Mock
    ContentReader contentReader;
    @Mock
    CountWriter countWriter;
    @Mock
    WordCounterService counterService;

    WordCounterApp wordCounterApp;

    @BeforeEach
    void setUp() {
        wordCounterApp = new WordCounterApp(contentReader, countWriter, counterService);
        Mockito.when(counterService.countWords(Mockito.any())).thenReturn(new WordCount(Map.of("hej", 1L)));
        Mockito.when(contentReader.contentSources()).thenReturn(List.of(() -> Stream.of("hej")));
    }

    @Test
    void countWords() {
        Assertions.assertThatNoException()
                .isThrownBy(() -> wordCounterApp.countWords());

        Mockito.verify(contentReader).contentSources();
        Mockito.verify(countWriter).writeCounts(Mockito.any());
        Mockito.verify(counterService).countWords(Mockito.any());
    }
}
