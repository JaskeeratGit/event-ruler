package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Patterns_anythingButSuffix_17_0_Test_testAnythingButSuffix_emptySuffix {


    @Test
    public void testAnythingButSuffix_emptySuffix() throws Exception {
        String suffix = "";
        Object result = Patterns.anythingButSuffix(suffix);
        assertNotNull(result, "Returned object should not be null");
        Class<?> clazz = result.getClass();
        // Verify MatchType field is set correctly
        Field matchTypeField = Arrays.stream(clazz.getDeclaredFields()).filter(f -> f.getType().equals(MatchType.class)).findFirst().orElseThrow(() -> new AssertionError("No MatchType-typed field found on result class"));
        matchTypeField.setAccessible(true);
        Object matchTypeValue = matchTypeField.get(result);
        assertSame(MatchType.ANYTHING_BUT_SUFFIX, matchTypeValue, "MatchType should be ANYTHING_BUT_SUFFIX");
        // Verify values contains the empty reversed string (still empty)
        Field valuesField = Arrays.stream(clazz.getDeclaredFields()).filter(f -> Collection.class.isAssignableFrom(f.getType())).findFirst().orElseThrow(() -> new AssertionError("No Collection-typed field found on result class"));
        valuesField.setAccessible(true);
        Object valuesObj = valuesField.get(result);
        assertNotNull(valuesObj, "Values collection field should not be null");
        @SuppressWarnings("unchecked")
        Collection<Object> values = (Collection<Object>) valuesObj;
        assertTrue(values.contains(""), "Values collection should contain the empty string");
    }

}
