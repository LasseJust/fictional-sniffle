package counter.outgoing.reader.filter;

import java.nio.file.Path;

public interface FileFilter {
    boolean includeFile(Path filePath);
}
