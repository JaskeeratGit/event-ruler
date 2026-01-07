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

class Patterns_equals_28_0_Test_equals_self {

    @Test
    void equals_self() {
        MatchType[] values = MatchType.values();
        MatchType t = values.length > 0 ? values[0] : null;
        Patterns p = new Patterns(t);
        assertTrue(p.equals(p), "An object must be equal to itself");
    }




}
