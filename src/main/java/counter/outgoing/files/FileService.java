package counter.outgoing.files;

import java.io.BufferedWriter;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileService {
    Stream<String> readLines(Path path);
    Stream<Path> listFiles(Path path);
    void createDir(Path path);
    BufferedWriter newBufferedWriter(Path path);
    boolean isDataFile(Path path);
}
