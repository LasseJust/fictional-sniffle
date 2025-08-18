package counter.outgoing.reader;

import counter.outgoing.reader.word.WordSource;

import java.util.List;

public interface ContentReader {
    List<WordSource> contentSources();
}
