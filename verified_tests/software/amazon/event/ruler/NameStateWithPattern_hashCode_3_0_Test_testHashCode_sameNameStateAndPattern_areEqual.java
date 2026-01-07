package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.annotation.Nullable;
import java.util.Objects;
import static java.util.Objects.requireNonNull;

/**
 * JUnit 5 tests for NameStateWithPattern.hashCode()
 */
class NameStateWithPattern_hashCode_3_0_Test_testHashCode_sameNameStateAndPattern_areEqual {

    // Helper subclasses to provide deterministic, different hash codes for NameState instances
    static class NameStateHashA extends NameState {

        @Override
        public int hashCode() {
            return 101;
        }
    }

    static class NameStateHashB extends NameState {

        @Override
        public int hashCode() {
            return 202;
        }
    }

    @Test
    void testHashCode_sameNameStateAndPattern_areEqual() throws Exception {
        NameState ns = new NameStateHashA();
        // construct Patterns instance via reflection (constructor takes MatchType which we pass as null)
        Constructor<?> ctor = Patterns.class.getDeclaredConstructors()[0];
        ctor.setAccessible(true);
        Patterns pattern = (Patterns) ctor.newInstance(new Object[] { null });
        NameStateWithPattern a = new NameStateWithPattern(ns, pattern);
        NameStateWithPattern b = new NameStateWithPattern(ns, pattern);
        // direct invocation
        assertEquals(a.hashCode(), b.hashCode(), "hashCode should be equal for same nameState and pattern");
        // reflective invocation
        Method hashMethod = NameStateWithPattern.class.getMethod("hashCode");
        Object reflected = hashMethod.invoke(a);
        assertTrue(reflected instanceof Integer);
        assertEquals(a.hashCode(), ((Integer) reflected).intValue(), "reflective hashCode should match direct call");
    }


}
