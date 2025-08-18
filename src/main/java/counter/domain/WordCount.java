package counter.domain;

import java.util.HashMap;
import java.util.Map;

public record WordCount(Map<String, Long> wordCounts) {

    public WordCount mergeCounts(WordCount otherWordCounts) {
        Map<String, Long> mergedCounts = new HashMap<>(wordCounts);
        otherWordCounts.wordCounts().forEach((word, count) -> mergedCounts.merge(word, count, Long::sum));
        return new WordCount(mergedCounts);
    }

}
