package counter.domain.port;

import counter.domain.model.WordCount;

public interface CountWriter {
    void writeCounts(WordCount wordCounts);
}
