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

public class ValuePatterns_toString_3_0_Test_toString_numericType_usesComparableNumberToIntValsAndSuper {

    @Test
    void toString_numericType_usesComparableNumberToIntValsAndSuper() {
        // numeric branch: type() == MatchType.NUMERIC_EQ
        ValuePatterns vp = new ValuePatterns(MatchType.NUMERIC_EQ, "12");
        String actual = vp.toString();
        // ComparableNumber.toIntVals("12") -> [49, 50]
        assertEquals("VP:[49, 50] (T:NUMERIC_EQ)", actual);
    }


}
