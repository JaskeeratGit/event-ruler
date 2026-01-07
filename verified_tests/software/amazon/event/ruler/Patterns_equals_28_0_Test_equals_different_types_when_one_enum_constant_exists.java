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

class Patterns_equals_28_0_Test_equals_different_types_when_one_enum_constant_exists {





    @Test
    void equals_different_types_when_one_enum_constant_exists() {
        MatchType[] values = MatchType.values();
        Assumptions.assumeTrue(values.length == 1, "This test requires exactly one MatchType enum constant");
        MatchType only = values[0];
        Patterns pWithOnly = new Patterns(only);
        Patterns pWithNull = new Patterns(null);
        assertFalse(pWithOnly.equals(pWithNull), "Patterns with one null and one non-null type must not be equal");
        assertFalse(pWithNull.equals(pWithOnly), "Equality must be symmetric for null vs non-null types");
    }
}
