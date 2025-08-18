package counter.domain;

import counter.outgoing.reader.word.WordSource;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordCounterService {

    public WordCount countWords(WordSource wordSource) {
        try (Stream<String> words = wordSource.words()) {
            Map<String, Long> counts = words.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            return new WordCount(counts);
        }
    }
}
