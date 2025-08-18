package counter.outgoing.reader.filter;

import counter.outgoing.files.FileService;

import java.nio.file.Path;
import java.util.Set;

public class ExcludeFileFilter implements FileFilter {
    private final Set<Path> excludeFileNames;
    private final FileService fileService;

    public ExcludeFileFilter(Set<Path> excludeFileNames,
                             FileService fileService) {
        this.excludeFileNames = excludeFileNames;
        this.fileService = fileService;
    }

    @Override
    public boolean includeFile(Path filePath) {
        return fileService.isDataFile(filePath) && !excludeFileNames.contains(filePath.getFileName());
    }
}
