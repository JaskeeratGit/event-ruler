package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

class Patterns_clone_27_0_Test_clone_whenSuperCloneSucceeds_returnsCloneOfRuntimeType {

    // A small subclass in the same package that implements Cloneable so Object.clone() will succeed.
    static class CloneablePatterns extends Patterns implements Cloneable {

        CloneablePatterns(final MatchType type) {
            super(type);
        }
    }


    @Test
    void clone_whenSuperCloneSucceeds_returnsCloneOfRuntimeType() throws Exception {
        // Create a subclass instance that implements Cloneable so Object.clone() will succeed.
        CloneablePatterns original = new CloneablePatterns(null);
        // Invoke clone() via reflection (inherited from Patterns)
        Method cloneMethod = Patterns.class.getDeclaredMethod("clone");
        cloneMethod.setAccessible(true);
        Object cloned = cloneMethod.invoke(original);
        assertNotNull(cloned, "clone() should not return null");
        assertTrue(cloned instanceof CloneablePatterns, "Returned object should be an instance of the runtime type (CloneablePatterns)");
        assertNotSame(original, cloned, "Returned object should be a different instance");
        // verify that the 'type' field was copied to the cloned instance
        Field typeField = Patterns.class.getDeclaredField("type");
        typeField.setAccessible(true);
        Object originalType = typeField.get(original);
        Object clonedType = typeField.get(cloned);
        assertEquals(originalType, clonedType, "The type field should be the same in the cloned instance");
    }
}
