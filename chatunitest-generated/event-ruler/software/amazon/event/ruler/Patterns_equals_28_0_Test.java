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

class Patterns_equals_28_0_Test {

    @Test
    void equals_self() {
        MatchType[] values = MatchType.values();
        MatchType t = values.length > 0 ? values[0] : null;
        Patterns p = new Patterns(t);
        assertTrue(p.equals(p), "An object must be equal to itself");
    }

    @Test
    void equals_null_and_different_class() {
        MatchType[] values = MatchType.values();
        MatchType t = values.length > 0 ? values[0] : null;
        Patterns p = new Patterns(t);
        assertFalse(p.equals(null), "Patterns should not be equal to null");
        assertFalse(p.equals(new Object()), "Patterns should not be equal to an instance of a different class");
    }

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
