package software.amazon.event.ruler;

import java.lang.reflect.Field;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Patterns_absencePatterns_24_0_Test_absencePatterns_returnsPatternsWithAbsentType {

    @Test
    public void absencePatterns_returnsPatternsWithAbsentType() throws Exception {
        Patterns patterns = Patterns.absencePatterns();
        assertNotNull(patterns, "absencePatterns() should not return null");
        // public accessor check
        assertEquals(MatchType.ABSENT, patterns.type(), "type() should return MatchType.ABSENT");
        // reflectively access the private final field 'type' to ensure internal state is set correctly
        Field typeField = Patterns.class.getDeclaredField("type");
        typeField.setAccessible(true);
        Object reflectedType = typeField.get(patterns);
        assertSame(MatchType.ABSENT, reflectedType, "private field 'type' should be MatchType.ABSENT");
    }

}
