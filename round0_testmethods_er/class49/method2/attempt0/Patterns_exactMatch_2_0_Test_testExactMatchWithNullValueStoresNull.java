package software.amazon.event.ruler;

import java.lang.reflect.Field;
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

public class Patterns_exactMatch_2_0_Test_testExactMatchWithNullValueStoresNull {


    @Test
    public void testExactMatchWithNullValueStoresNull() throws Exception {
        Object valuePatterns = Patterns.exactMatch(null);
        assertNotNull(valuePatterns, "exactMatch should not return null even when passed null");
        Class<?> vpClass = valuePatterns.getClass();
        Field stringField = null;
        for (Field f : vpClass.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.getType().equals(String.class)) {
                stringField = f;
                break;
            }
        }
        if (stringField == null) {
            for (Field f : vpClass.getDeclaredFields()) {
                f.setAccessible(true);
                if (CharSequence.class.isAssignableFrom(f.getType())) {
                    stringField = f;
                    break;
                }
            }
        }
        assertNotNull(stringField, "Could not locate a String/CharSequence field in ValuePatterns");
        Object storedValue = stringField.get(valuePatterns);
        assertNull(storedValue, "When passing null to exactMatch, the stored value should be null");
    }

}
