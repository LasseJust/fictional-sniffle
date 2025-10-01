package counter.domain.model;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface WordSource {
    Stream<String> words();

    default WordCount countWords() {
        try (Stream<String> words = words()) {
            Map<String, Long> counts = words.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            return new WordCount(counts);
        }
    }
}
