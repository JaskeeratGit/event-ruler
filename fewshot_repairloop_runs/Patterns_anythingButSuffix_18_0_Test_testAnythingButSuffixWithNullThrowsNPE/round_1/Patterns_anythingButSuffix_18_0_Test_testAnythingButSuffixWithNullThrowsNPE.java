package software.amazon.event.ruler;

import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Patterns_anythingButSuffix_18_0_Test_testAnythingButSuffixWithNullThrowsNPE {

    @Test
    public void testAnythingButSuffixWithNullThrowsNPE() {
        Executable call = () -> Patterns.anythingButSuffix((Set<String>) null);
        assertThrows(NullPointerException.class, call, "Passing null should throw NullPointerException");
    }

    /**
     * Finds the first declared field in the given object's class that is assignable to the given type.
     */
    private static Field findFieldByType(Object obj, Class<?> type) {
        Class<?> cls = obj.getClass();
        for (Field f : cls.getDeclaredFields()) {
            if (type.isAssignableFrom(f.getType())) {
                f.setAccessible(true);
                return f;
            }
        }
        // If not found directly on the class, check superclasses
        Class<?> superCls = cls.getSuperclass();
        while (superCls != null && superCls != Object.class) {
            for (Field f : superCls.getDeclaredFields()) {
                if (type.isAssignableFrom(f.getType())) {
                    f.setAccessible(true);
                    return f;
                }
            }
            superCls = superCls.getSuperclass();
        }
        return null;
    }
}
