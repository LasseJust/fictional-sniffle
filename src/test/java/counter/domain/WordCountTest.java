package counter.domain;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.MapEntry;
import org.junit.jupiter.api.Test;

import java.util.Map;


class WordCountTest {

    @Test
    void mergeCounts() {
        WordCount count1 = new WordCount(Map.of("hej", 1L, "med", 2L, "dig", 3L));
        WordCount count2 = new WordCount(Map.of("dig", 3L, "lasse", 1L));

        WordCount mergedCounts = count1.mergeCounts(count2);

        Assertions.assertThat(mergedCounts.wordCounts())
                .containsOnly(
                        MapEntry.entry("hej", 1L),
                        MapEntry.entry("med", 2L),
                        MapEntry.entry("dig", 6L),
                        MapEntry.entry("lasse", 1L));

    }
}
