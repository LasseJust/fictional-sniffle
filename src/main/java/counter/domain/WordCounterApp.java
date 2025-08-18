package counter.domain;


import counter.outgoing.reader.ContentReader;
import counter.outgoing.writer.CountWriter;

import java.util.Map;

public class WordCounterApp {

    private final ContentReader contentReader;
    private final CountWriter countWriter;
    private final WordCounterService wordCounterService;

    public WordCounterApp(ContentReader contentReader,
                          CountWriter countWriter,
                          WordCounterService wordCounterService) {
        this.contentReader = contentReader;
        this.countWriter = countWriter;
        this.wordCounterService = wordCounterService;
    }

    public void countWords() {
        WordCount combinedWordCount = contentReader.contentSources()
                .parallelStream()
                .map(wordCounterService::countWords)
                .reduce(new WordCount(Map.of()), WordCount::mergeCounts);
        countWriter.writeCounts(combinedWordCount);
    }
}
