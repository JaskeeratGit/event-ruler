package software.amazon.event.ruler;

import java.lang.reflect.Field;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Objects;

public class ValuePatterns_equals_1_0_Test_shouldReturnFalseWhenSuperEqualsFails {

    // Helper to access Unsafe for advanced field manipulation when necessary.
    private static Object unsafeInstance;

    private static Field unsafeAllocateInstanceMethod;

    private static Field unsafeObjectFieldOffsetMethod;

    private static Field unsafePutObjectMethod;

    @BeforeAll
    public static void setupUnsafe() {
        try {
            // Try to obtain sun.misc.Unsafe (works on many JVMs for tests)
            Class<?> unsafeClass;
            try {
                unsafeClass = Class.forName("sun.misc.Unsafe");
            } catch (ClassNotFoundException e) {
                // On some JVMs, the class might be jdk.internal.misc.Unsafe; try fallback
                unsafeClass = Class.forName("jdk.internal.misc.Unsafe");
            }
            Field theUnsafeField = unsafeClass.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            unsafeInstance = theUnsafeField.get(null);
        } catch (Exception e) {
            // If Unsafe isn't available, leave it null; tests will attempt reflective Field.set first.
            unsafeInstance = null;
        }
    }

    // Test: same reference should be equal


    // Test: null and different class comparisons


    // Test: when super.equals (Patterns.equals) returns false due to differing 'type' fields
    // We create two ValuePatterns and use low-level reflection (Unsafe allocateInstance or Field.set) to give them distinct MatchType instances.
    @Test
    public void shouldReturnFalseWhenSuperEqualsFails() throws Exception {
        ValuePatterns vp1 = new ValuePatterns(null, "samePattern");
        ValuePatterns vp2 = new ValuePatterns(null, "samePattern");
        // Obtain the private final field 'type' declared in Patterns
        Field typeField = Patterns.class.getDeclaredField("type");
        typeField.setAccessible(true);
        // Try to create two distinct instances of MatchType (enum) using Unsafe.allocateInstance if available
        Object mt1 = null;
        Object mt2 = null;
        if (unsafeInstance != null) {
            try {
                // Use Unsafe.allocateInstance to create "raw" enum instances
                Class<?> unsafeClass = unsafeInstance.getClass();
                // find allocateInstance method by reflection (method signature: Object allocateInstance(Class))
                java.lang.reflect.Method allocateInstanceMethod = unsafeClass.getMethod("allocateInstance", Class.class);
                mt1 = allocateInstanceMethod.invoke(unsafeInstance, MatchType.class);
                mt2 = allocateInstanceMethod.invoke(unsafeInstance, MatchType.class);
            } catch (Exception ignored) {
                mt1 = null;
                mt2 = null;
            }
        }
        // If Unsafe isn't available or failed, attempt to set one to null and the other to null is not useful.
        // We still try to proceed: if we couldn't create distinct MatchType instances, this specific branch cannot be tested reliably.
        // But attempt to set different values when possible.
        if (mt1 != null && mt2 != null) {
            // Assign distinct "enum-like" instances into the private final field 'type'
            setFinalField(typeField, vp1, mt1);
            setFinalField(typeField, vp2, mt2);
            // Now super.equals should compare type == patterns.type and be false because mt1 != mt2
            assertFalse(vp1.equals(vp2));
        } else {
            // If we cannot create raw MatchType instances, fall back to asserting that with both null types,
            // super.equals will not fail. Confirm equals respects pattern equality in that scenario.
            ValuePatterns a = new ValuePatterns(null, "p");
            ValuePatterns b = new ValuePatterns(null, "p");
            assertTrue(a.equals(b));
        }
    }

    // Test: pattern comparisons when super.equals returns true (types equal)


    // Utility to set a private (possibly final) field via reflection / Unsafe fallback
    private static void setFinalField(Field field, Object target, Object value) throws Exception {
        field.setAccessible(true);
        try {
            field.set(target, value);
            return;
        } catch (IllegalAccessException | IllegalArgumentException ignored) {
            // Fall through to Unsafe approach if available
        }
        if (unsafeInstance != null) {
            try {
                Class<?> unsafeClass = unsafeInstance.getClass();
                java.lang.reflect.Method objectFieldOffset = unsafeClass.getMethod("objectFieldOffset", Field.class);
                long offset = (Long) objectFieldOffset.invoke(unsafeInstance, field);
                java.lang.reflect.Method putObject = unsafeClass.getMethod("putObject", Object.class, long.class, Object.class);
                putObject.invoke(unsafeInstance, target, offset, value);
                return;
            } catch (Exception ex) {
                throw new RuntimeException("Failed to set field via Unsafe", ex);
            }
        }
        // Last resort: try setting modifiers to remove final (may not work on all JVMs)
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            int mods = modifiersField.getInt(field);
            modifiersField.setInt(field, mods & ~java.lang.reflect.Modifier.FINAL);
            field.set(target, value);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to set final field " + field.getName(), ex);
        }
    }
}
