package counter.outgoing.writer;

import counter.domain.WordCount;

public interface CountWriter {
    void writeCounts(WordCount wordCounts);
}
