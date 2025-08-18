package counter.ingoing.cli;

import java.nio.file.Files;
import java.nio.file.Path;

public record CmdLineArgs(Path inputPath, Path outputPath, Path excludeFilePath) {
    public CmdLineArgs {
        if (!Files.isDirectory(inputPath)) {
            throw new IllegalArgumentException("Input path is not a directory");
        }
        if (Files.exists(outputPath) && !Files.isDirectory(outputPath)) {
            throw new IllegalArgumentException("Output path exists but is not a directory");
        }
        if (!Files.isRegularFile(excludeFilePath)) {
            throw new IllegalArgumentException("Exclude list file is not a valid file");
        }
    }
}
