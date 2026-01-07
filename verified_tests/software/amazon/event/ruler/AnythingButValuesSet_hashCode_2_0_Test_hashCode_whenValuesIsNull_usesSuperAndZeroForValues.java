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

public class AnythingButValuesSet_hashCode_2_0_Test_hashCode_whenValuesIsNull_usesSuperAndZeroForValues {

    @Test
    public void hashCode_whenValuesIsNull_usesSuperAndZeroForValues() throws Exception {
        // create instance (constructor has package-private visibility; test is in same package)
        AnythingButValuesSet instance = new AnythingButValuesSet(null, null);
        // invoke hashCode via reflection (as requested)
        Method hashCodeMethod = AnythingButValuesSet.class.getDeclaredMethod("hashCode");
        hashCodeMethod.setAccessible(true);
        int actual = (Integer) hashCodeMethod.invoke(instance);
        // expected uses identity hash (super.hashCode()) plus 0 for null values
        int expected = 31 * System.identityHashCode(instance) + 0;
        assertEquals(expected, actual);
    }


}
