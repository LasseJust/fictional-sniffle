package counter.domain.tokenizer;

import java.util.stream.Stream;

public interface Tokenizer {
    Stream<String> tokenize(String text);
}
