package counter.outgoing.reader.filter;

import counter.outgoing.shared.file.FileService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class ExcludeFileFilterTest {

    @Mock
    FileService fileService;

    ExcludeFileFilter excludeFileFilter;

    @BeforeEach
    void setUp() {
        excludeFileFilter = new ExcludeFileFilter(Set.of(Path.of("f1")), fileService);
        Mockito.when(fileService.isDataFile(Mockito.any())).thenReturn(true);
    }

    @Test
    void includeFile_shouldBeIncluded() {
        boolean include = excludeFileFilter.includeFile(Path.of("f2"));

        Assertions.assertThat(include).isTrue();
    }

    @Test
    void includeFile_shouldBeExcluded() {
        boolean include = excludeFileFilter.includeFile(Path.of("f1"));

        Assertions.assertThat(include).isFalse();
    }
}
