package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.annotation.Nullable;
import java.util.Objects;
import static java.util.Objects.requireNonNull;

class NameStateWithPattern_equals_2_0_Test_equals_patternNullVsNonNull_falseOrThrowsAsExpected {

    // Helper to create a Patterns instance via reflection (constructor takes MatchType, supply null)
    private Patterns createPatterns() throws Exception {
        Class<?> patternsClass = Class.forName("software.amazon.event.ruler.Patterns");
        Constructor<?> ctor = patternsClass.getDeclaredConstructors()[0];
        ctor.setAccessible(true);
        Object p = ctor.newInstance(new Object[] { null });
        return (Patterns) p;
    }





    @Test
    void equals_patternNullVsNonNull_falseOrThrowsAsExpected() throws Exception {
        NameState ns = new NameState();
        Patterns p = createPatterns();
        // Case: this.pattern non-null, other.pattern null -> pattern.equals(null) returns false
        NameStateWithPattern withNonNullPattern = new NameStateWithPattern(ns, p);
        NameStateWithPattern withNullPattern = new NameStateWithPattern(ns, null);
        assertFalse(withNonNullPattern.equals(withNullPattern));
        // Case: this.pattern null, other.pattern non-null -> calling pattern.equals(...) on null -> NPE
        assertThrows(NullPointerException.class, () -> withNullPattern.equals(withNonNullPattern));
    }
}
