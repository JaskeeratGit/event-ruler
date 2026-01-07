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

public class Patterns_hashCode_29_0_Test_hashCode_returnsZero_whenTypeIsNull {

    @Test
    public void hashCode_returnsZero_whenTypeIsNull() throws Exception {
        Patterns patterns = createPatternsWithType(null);
        assertEquals(0, patterns.hashCode());
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
