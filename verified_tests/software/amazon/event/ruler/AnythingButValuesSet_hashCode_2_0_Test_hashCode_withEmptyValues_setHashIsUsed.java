package software.amazon.event.ruler;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.Objects;

public class AnythingButValuesSet_hashCode_2_0_Test_hashCode_withEmptyValues_setHashIsUsed {



    @Test
    public void hashCode_withEmptyValues_setHashIsUsed() throws Exception {
        Set<String> empty = new HashSet<>();
        AnythingButValuesSet instance = new AnythingButValuesSet(null, empty);
        Method hashCodeMethod = AnythingButValuesSet.class.getDeclaredMethod("hashCode");
        hashCodeMethod.setAccessible(true);
        int actual = (Integer) hashCodeMethod.invoke(instance);
        Set<String> internalValues = instance.getValues();
        int valuesHash = internalValues != null ? internalValues.hashCode() : 0;
        int expected = 31 * System.identityHashCode(instance) + valuesHash;
        assertEquals(expected, actual);
    }
}
