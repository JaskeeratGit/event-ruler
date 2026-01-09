package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Objects;

public class ValuePatterns_toString_3_0_Test {

    @Test
    void toString_numericType_usesComparableNumberToIntValsAndSuper() {
        // numeric branch: type() == MatchType.NUMERIC_EQ
        ValuePatterns vp = new ValuePatterns(MatchType.NUMERIC_EQ, "12");
        String actual = vp.toString();
        // ComparableNumber.toIntVals("12") -> [49, 50]
        assertEquals("VP:[49, 50] (T:NUMERIC_EQ)", actual);
    }

    @Test
    void toString_nonNumericType_usesPatternAndSuper_whenTypeIsNotNumericEq() {
        // Force non-numeric branch by overriding type() to return null (so it is != NUMERIC_EQ).
        ValuePatterns vp = new ValuePatterns(MatchType.NUMERIC_EQ, "abc") {

            @Override
            public MatchType type() {
                return null;
            }
        };
        String actual = vp.toString();
        // super.toString() uses the stored type from Patterns (the constructor argument),
        // so it will still show T:NUMERIC_EQ in the super.toString() portion.
        assertEquals("VP:abc (T:NUMERIC_EQ)", actual);
    }

    @Test
    void comparableNumber_toIntVals_and_privateConstructor_viaReflection() throws Exception {
        // Invoke private constructor of ComparableNumber via reflection to satisfy reflective use
        Constructor<ComparableNumber> ctor = ComparableNumber.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        ComparableNumber inst = ctor.newInstance();
        assertNotNull(inst);
        // Invoke toIntVals(String) reflectively
        Method toIntVals = ComparableNumber.class.getDeclaredMethod("toIntVals", String.class);
        toIntVals.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Integer> result = (List<Integer>) toIntVals.invoke(null, "A1");
        // 'A' -> 65, '1' -> 49
        assertEquals(Arrays.asList(65, 49), result);
    }
}
