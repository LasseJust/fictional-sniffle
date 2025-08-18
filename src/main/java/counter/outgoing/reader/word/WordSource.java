package counter.outgoing.reader.word;

import java.util.stream.Stream;

public interface WordSource {
    Stream<String> words();
}
