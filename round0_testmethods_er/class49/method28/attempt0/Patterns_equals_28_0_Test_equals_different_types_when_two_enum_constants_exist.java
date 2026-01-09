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

class Patterns_equals_28_0_Test_equals_different_types_when_two_enum_constants_exist {




    @Test
    void equals_different_types_when_two_enum_constants_exist() {
        MatchType[] values = MatchType.values();
        Assumptions.assumeTrue(values.length >= 2, "This test requires at least two MatchType enum constants");
        MatchType a = values[0];
        MatchType b = values[1];
        Patterns p1 = new Patterns(a);
        Patterns p2 = new Patterns(b);
        assertFalse(p1.equals(p2), "Patterns with different non-null MatchType constants must not be equal");
    }

}
