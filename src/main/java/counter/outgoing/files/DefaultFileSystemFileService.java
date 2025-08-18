package counter.outgoing.files;

import counter.outgoing.reader.DataReadException;
import counter.outgoing.writer.DataWriteException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class DefaultFileSystemFileService implements FileService {
    @Override
    public Stream<String> readLines(Path path) {
        try {
            return Files.lines(path);
        } catch (IOException e) {
            throw new DataReadException(e);
        }
    }

    @Override
    public Stream<Path> listFiles(Path path) {
        try {
            return Files.list(path);
        } catch (IOException e) {
            throw new DataReadException(e);
        }
    }

    @Override
    public void createDir(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new DataWriteException(e);
        }
    }

    @Override
    public BufferedWriter newBufferedWriter(Path path) {
        try {
            return Files.newBufferedWriter(path);
        } catch (IOException e) {
            throw new DataWriteException(e);
        }
    }


    @Override
    public boolean isDataFile(Path path) {
        try {
            return Files.isRegularFile(path)
                    && !Files.isHidden(path);
        } catch (IOException e) {
            throw new DataReadException(e);
        }
    }
}
