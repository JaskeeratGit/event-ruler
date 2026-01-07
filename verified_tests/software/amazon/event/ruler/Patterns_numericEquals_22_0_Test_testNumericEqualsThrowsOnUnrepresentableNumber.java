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

public class Patterns_numericEquals_22_0_Test_testNumericEqualsThrowsOnUnrepresentableNumber {



    @Test
    public void testNumericEqualsThrowsOnUnrepresentableNumber() {
        // A very large exponent that will produce a double infinite value and should trigger IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> Patterns.numericEquals("1e400"));
    }
}
