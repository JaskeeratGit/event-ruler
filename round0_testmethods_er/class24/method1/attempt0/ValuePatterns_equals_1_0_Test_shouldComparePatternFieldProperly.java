package software.amazon.event.ruler;

import java.lang.reflect.Field;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Objects;

public class ValuePatterns_equals_1_0_Test_shouldComparePatternFieldProperly {

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


    // Test: pattern comparisons when super.equals returns true (types equal)
    @Test
    public void shouldComparePatternFieldProperly() throws Exception {
        // Two instances with null types (equal) and same pattern -> equal
        ValuePatterns vp1 = new ValuePatterns(null, "pat");
        ValuePatterns vp2 = new ValuePatterns(null, "pat");
        assertTrue(vp1.equals(vp2));
        // Different pattern -> not equal
        ValuePatterns vp3 = new ValuePatterns(null, "pat1");
        ValuePatterns vp4 = new ValuePatterns(null, "pat2");
        assertFalse(vp3.equals(vp4));
        // Both patterns null -> equal
        ValuePatterns vp5 = new ValuePatterns(null, null);
        ValuePatterns vp6 = new ValuePatterns(null, null);
        assertTrue(vp5.equals(vp6));
        // One null pattern one non-null -> not equal (when types equal)
        ValuePatterns vp7 = new ValuePatterns(null, null);
        ValuePatterns vp8 = new ValuePatterns(null, "x");
        assertFalse(vp7.equals(vp8));
    }

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
