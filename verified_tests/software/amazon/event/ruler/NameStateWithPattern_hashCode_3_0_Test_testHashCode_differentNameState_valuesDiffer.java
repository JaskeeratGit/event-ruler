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
class NameStateWithPattern_hashCode_3_0_Test_testHashCode_differentNameState_valuesDiffer {

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
    void testHashCode_differentNameState_valuesDiffer() {
        NameState ns1 = new NameStateHashA();
        NameState ns2 = new NameStateHashB();
        NameStateWithPattern a = new NameStateWithPattern(ns1, null);
        NameStateWithPattern b = new NameStateWithPattern(ns2, null);
        // Given deterministic overridden hashCode implementations for the NameState subclasses,
        // the resulting combined hash should differ.
        assertNotEquals(a.hashCode(), b.hashCode(), "hashCode should differ for different nameState hashCodes");
    }
}
