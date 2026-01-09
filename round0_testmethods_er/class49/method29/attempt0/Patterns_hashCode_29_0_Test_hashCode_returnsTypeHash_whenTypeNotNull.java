package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import sun.misc.Unsafe;
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

public class Patterns_hashCode_29_0_Test_hashCode_returnsTypeHash_whenTypeNotNull {


    @Test
    public void hashCode_returnsTypeHash_whenTypeNotNull() throws Exception {
        Patterns patterns = createPatternsWithType(null);
        // Allocate an instance of the enum MatchType without invoking constructors
        Unsafe unsafe = getUnsafe();
        Object matchTypeInstance = unsafe.allocateInstance(MatchType.class);
        // Inject the allocated MatchType instance into the private final field 'type'
        Field typeField = Patterns.class.getDeclaredField("type");
        typeField.setAccessible(true);
        typeField.set(patterns, matchTypeInstance);
        int expected = matchTypeInstance.hashCode();
        assertEquals(expected, patterns.hashCode());
        // Call again to ensure stability
        assertEquals(expected, patterns.hashCode());
    }

    private Patterns createPatternsWithType(MatchType type) throws Exception {
        Constructor<Patterns> ctor = Patterns.class.getDeclaredConstructor(MatchType.class);
        ctor.setAccessible(true);
        return ctor.newInstance(type);
    }

    private Unsafe getUnsafe() throws Exception {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (Unsafe) f.get(null);
    }
}
