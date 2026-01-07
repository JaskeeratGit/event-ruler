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
class NameStateWithPattern_hashCode_3_0_Test_testHashCode_nullPattern_sameNameState_areEqual {

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
    void testHashCode_nullPattern_sameNameState_areEqual() throws Exception {
        NameState ns = new NameStateHashA();
        NameStateWithPattern a = new NameStateWithPattern(ns, null);
        NameStateWithPattern b = new NameStateWithPattern(ns, null);
        assertEquals(a.hashCode(), b.hashCode(), "hashCode should be equal when pattern is null and nameState same");
        // reflective invocation as well
        Method hashMethod = NameStateWithPattern.class.getMethod("hashCode");
        int reflected = (Integer) hashMethod.invoke(b);
        assertEquals(b.hashCode(), reflected);
    }

}
