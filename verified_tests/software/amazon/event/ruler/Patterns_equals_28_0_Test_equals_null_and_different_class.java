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

class Patterns_equals_28_0_Test_equals_null_and_different_class {


    @Test
    void equals_null_and_different_class() {
        MatchType[] values = MatchType.values();
        MatchType t = values.length > 0 ? values[0] : null;
        Patterns p = new Patterns(t);
        assertFalse(p.equals(null), "Patterns should not be equal to null");
        assertFalse(p.equals(new Object()), "Patterns should not be equal to an instance of a different class");
    }



}
