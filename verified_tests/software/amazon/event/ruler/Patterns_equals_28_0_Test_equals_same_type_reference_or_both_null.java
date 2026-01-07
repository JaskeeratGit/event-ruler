package software.amazon.event.ruler;

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

class Patterns_equals_28_0_Test_equals_same_type_reference_or_both_null {



    @Test
    void equals_same_type_reference_or_both_null() {
        MatchType[] values = MatchType.values();
        MatchType t = values.length > 0 ? values[0] : null;
        Patterns p1 = new Patterns(t);
        Patterns p2 = new Patterns(t);
        // If t is null, both patterns have null types -> should be equal.
        // If t is non-null, both patterns share same enum constant reference -> should be equal.
        assertTrue(p1.equals(p2), "Patterns with the same type reference (including both null) must be equal");
    }


}
