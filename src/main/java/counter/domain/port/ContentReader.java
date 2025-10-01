package counter.domain.port;

import counter.domain.model.WordSource;

import java.util.List;

public interface ContentReader {
    List<WordSource> contentSources();
}
