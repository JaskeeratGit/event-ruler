package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
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

public class Patterns_anythingButSuffix_17_0_Test_testAnythingButSuffix_nullSuffix_throwsNullPointerException {



    @Test
    public void testAnythingButSuffix_nullSuffix_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Patterns.anythingButSuffix(null), "Passing null suffix should throw NullPointerException");
    }
}
