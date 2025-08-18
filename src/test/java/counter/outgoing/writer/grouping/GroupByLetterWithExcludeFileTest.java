package counter.outgoing.writer.grouping;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;


class GroupByLetterWithExcludeFileTest {

    GroupByLetterWithExcludeFile groupingStrategy;

    @BeforeEach
    void setUp() {
        groupingStrategy = new GroupByLetterWithExcludeFile(Set.of("ignore", "this"), "exclude_filename.txt");
    }

    @Test
    void group_firstLetter() {
        String outputGroup = groupingStrategy.group("word");

        Assertions.assertThat(outputGroup).isEqualTo("FILE_W.txt");
    }

    @Test
    void group_ignored() {
        String outputGroup = groupingStrategy.group("ignore");

        Assertions.assertThat(outputGroup).isEqualTo("exclude_filename.txt");
    }
}