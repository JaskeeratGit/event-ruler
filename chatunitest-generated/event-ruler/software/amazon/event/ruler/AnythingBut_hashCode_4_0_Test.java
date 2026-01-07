package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Objects;

public class AnythingBut_hashCode_4_0_Test {

    private int computeExpectedHash(final AnythingBut instance, final Set<String> values, final boolean isNumeric) {
        try {
            // invoke the superclass's hashCode (equivalent to super.hashCode() in the class under test)
            Method superHashMethod = instance.getClass().getSuperclass().getDeclaredMethod("hashCode");
            superHashMethod.setAccessible(true);
            int superHash = ((Integer) superHashMethod.invoke(instance)).intValue();
            int result = superHash;
            result = 31 * result + (values != null ? values.hashCode() : 0);
            result = 31 * result + (isNumeric ? 1 : 0);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AnythingBut createSubjectForTest(Set<String> values, boolean isNumeric) throws Exception {
        // If values is null, avoid passing null to the constructor (it may throw). Create with a non-null
        // placeholder and then set the field to null via reflection.
        Set<String> ctorValues = values != null ? values : Collections.emptySet();
        AnythingBut instance = new AnythingBut(ctorValues, isNumeric);
        // If we need to adjust the private fields (e.g., set values to null), do so via reflection to match test case.
        Field valuesField = AnythingBut.class.getDeclaredField("values");
        valuesField.setAccessible(true);
        // can set null
        valuesField.set(instance, values);
        Field isNumericField = AnythingBut.class.getDeclaredField("isNumeric");
        isNumericField.setAccessible(true);
        isNumericField.setBoolean(instance, isNumeric);
        return instance;
    }

    @Test
    public void testHashCode_NullValues_IsNumericFalse() throws Exception {
        // values = null, isNumeric = false -> tests the values==null branch and isNumeric false branch
        AnythingBut subject = createSubjectForTest(null, false);
        Method hashCodeMethod = AnythingBut.class.getMethod("hashCode");
        Object invoked = hashCodeMethod.invoke(subject);
        int actual = ((Integer) invoked).intValue();
        int expected = computeExpectedHash(subject, null, false);
        assertEquals(expected, actual);
    }

    @Test
    public void testHashCode_EmptyValues_IsNumericTrue() throws Exception {
        // values = empty set (non-null), isNumeric = true -> tests values != null and isNumeric true branch
        Set<String> values = Collections.emptySet();
        AnythingBut subject = createSubjectForTest(values, true);
        Method hashCodeMethod = AnythingBut.class.getMethod("hashCode");
        Object invoked = hashCodeMethod.invoke(subject);
        int actual = ((Integer) invoked).intValue();
        int expected = computeExpectedHash(subject, values, true);
        assertEquals(expected, actual);
    }

    @Test
    public void testHashCode_NonEmptyValues_IsNumericFalse() throws Exception {
        // values non-empty set to ensure values.hashCode() contributes to final result
        Set<String> values = new HashSet<>();
        values.add("A");
        AnythingBut subject = createSubjectForTest(values, false);
        Method hashCodeMethod = AnythingBut.class.getMethod("hashCode");
        Object invoked = hashCodeMethod.invoke(subject);
        int actual = ((Integer) invoked).intValue();
        int expected = computeExpectedHash(subject, values, false);
        assertEquals(expected, actual);
    }
}
