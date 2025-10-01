package counter.domain.tokenizer;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class LowerCaseTrimmingTokenizer implements Tokenizer {

    @Override
    public Stream<String> tokenize(String text) {
        String withoutPunctuation = text.replaceAll("[^a-zA-Z0-9\\s']", " ");
        String[] words = withoutPunctuation.split("\\s+");
        return Arrays.stream(words)
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .map(String::toLowerCase);
    }
}
