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

public class ValuePatterns_toString_3_0_Test_toString_nonNumericType_usesPatternAndSuper_whenTypeIsNotNumericEq {


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

}
