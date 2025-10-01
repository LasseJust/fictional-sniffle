package counter.outgoing.reader.tokenizer;

import counter.domain.tokenizer.LowerCaseTrimmingTokenizer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;


class LowerCaseTrimmingTokenizerTest {

    LowerCaseTrimmingTokenizer tokenizer = new LowerCaseTrimmingTokenizer();

    @Test
    void tokenize_plain() {
        Stream<String> words = tokenizer.tokenize("some text");

        Assertions.assertThat(words).containsExactlyInAnyOrder("some", "text");
    }

    @Test
    void tokenize_withPunctuation() {
        Stream<String> words = tokenizer.tokenize("some text, with punctuation!");

        Assertions.assertThat(words).containsExactlyInAnyOrder("some", "text", "with", "punctuation");
    }

    @Test
    void tokenize_caseInsensitive() {
        Stream<String> words = tokenizer.tokenize("SOME text");

        Assertions.assertThat(words).containsExactlyInAnyOrder("some", "text");
    }

    @Test
    void tokenize_repeatWords() {
        Stream<String> words = tokenizer.tokenize("some text some text");

        Assertions.assertThat(words).containsExactlyInAnyOrder("some", "some", "text", "text");
    }
}
