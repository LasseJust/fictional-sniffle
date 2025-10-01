package counter.domain;


import counter.domain.model.WordCount;
import counter.domain.model.WordSource;
import counter.domain.port.ContentReader;
import counter.domain.port.CountWriter;

import java.util.Map;

public class WordCounterService {

    private final ContentReader contentReader;
    private final CountWriter countWriter;

    public WordCounterService(ContentReader contentReader,
                              CountWriter countWriter) {
        this.contentReader = contentReader;
        this.countWriter = countWriter;
    }

    public void countWords() {
        WordCount combinedWordCount = contentReader.contentSources()
                .parallelStream()
                .map(WordSource::countWords)
                .reduce(new WordCount(Map.of()), WordCount::mergeCounts);
        countWriter.writeCounts(combinedWordCount);
    }
}
