package counter.domain;

import counter.outgoing.reader.word.WordSource;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.MapEntry;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;


class WordCounterServiceTest {

    WordCounterService wordCounterService = new WordCounterService();

    @Test
    void countWords() {
        WordSource wordSource = () -> Stream.of("hej", "hej", "med", "dig");

        WordCount wordCount = wordCounterService.countWords(wordSource);

        Assertions.assertThat(wordCount.wordCounts())
                .containsOnly(
                        MapEntry.entry("hej", 2L),
                        MapEntry.entry("med", 1L),
                        MapEntry.entry("dig", 1L));
    }
}
