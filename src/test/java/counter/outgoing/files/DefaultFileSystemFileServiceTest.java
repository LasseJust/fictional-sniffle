package counter.outgoing.files;

import counter.outgoing.reader.DataReadException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;


class DefaultFileSystemFileServiceTest {

    @TempDir
    Path tempDir;

    FileService fileService = new DefaultFileSystemFileService();

    @Test
    void createDir() {
        Path outDir = tempDir.resolve("output");

        fileService.createDir(outDir);

        Assertions.assertThat(outDir)
                .exists()
                .isDirectory();

    }

    @Test
    void newBufferedWriter_readLines() throws IOException {
        Path file = tempDir.resolve("a.txt");

        try (BufferedWriter writer = fileService.newBufferedWriter(file)) {
            writer.write("hej");
            writer.newLine();
            writer.write("med");
        }

        List<String> lines = fileService.readLines(file).toList();

        Assertions.assertThat(lines).containsExactlyInAnyOrder("hej", "med");
    }

    @Test
    void listFiles() throws IOException {
        Path file1 = tempDir.resolve("f1.txt");
        Path file2 = tempDir.resolve("f2.txt");

        Files.writeString(file1, "hej");
        Files.writeString(file2, "med");

        List<Path> paths = fileService.listFiles(tempDir).toList();

        Assertions.assertThat(paths).containsExactlyInAnyOrder(file1, file2);
    }

    @Test
    void isDataFile_regularFile_true() throws IOException {
        Path file = tempDir.resolve("f1.txt");
        Files.writeString(file, "hej");

        boolean isDataFile = fileService.isDataFile(file);
        Assertions.assertThat(isDataFile).isTrue();
    }

    @Test
    void isDataFile_directory_false() throws IOException {
        Path dir = tempDir.resolve("dir");
        Files.createDirectory(dir);

        boolean isDataFile = fileService.isDataFile(dir);
        Assertions.assertThat(isDataFile).isFalse();
    }

    @Test
    void isDataFile_hiddenFile_false() throws IOException {
        Path file = tempDir.resolve(".hidden");
        Files.writeString(file, "hej");

        boolean isDataFile = fileService.isDataFile(file);
        Assertions.assertThat(isDataFile).isFalse();
    }

    @Test
    void readLines_nonExistentFile_dataReadExcetpionThrown() {
        Path nonExistent = tempDir.resolve("invalid.txt");

        Assertions.assertThatExceptionOfType(DataReadException.class)
                .isThrownBy(() -> fileService.readLines(nonExistent))
                .withCauseInstanceOf(NoSuchFileException.class);
    }
}
