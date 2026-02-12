package software.amazon.event.ruler;

import org.junit.Test;
import java.lang.reflect.Field;
import java.util.Set;

public class Patterns_anythingButSuffix_18_0_Test_testAnythingButSuffixWithNullThrowsNPE {

    @Test(expected = NullPointerException.class)
    public void testAnythingButSuffixWithNullThrowsNPE() {
        Patterns.anythingButSuffix((Set<String>) null);
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
