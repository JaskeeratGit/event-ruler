package software.amazon.event.ruler;

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

public class AnythingBut_hashCode_4_0_Test_testHashCode_EmptyValues_IsNumericTrue {

    private int computeExpectedHash(final AnythingBut instance, final Set<String> values, final boolean isNumeric) {
        int superHash = System.identityHashCode(instance);
        int result = superHash;
        result = 31 * result + (values != null ? values.hashCode() : 0);
        result = 31 * result + (isNumeric ? 1 : 0);
        return result;
    }


    @Test
    public void testHashCode_EmptyValues_IsNumericTrue() throws Exception {
        // values = empty set (non-null), isNumeric = true -> tests values != null and isNumeric true branch
        Set<String> values = Collections.emptySet();
        AnythingBut subject = new AnythingBut(values, true);
        Method hashCodeMethod = AnythingBut.class.getMethod("hashCode");
        Object invoked = hashCodeMethod.invoke(subject);
        int actual = ((Integer) invoked).intValue();
        int expected = computeExpectedHash(subject, values, true);
        assertEquals(expected, actual);
    }

}
