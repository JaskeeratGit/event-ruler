package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

public class Patterns_wildcardMatch_26_0_Test {

    private static final String PATTERNS_CLASS = "software.amazon.event.ruler.Patterns";

    private static final String MATCH_TYPE_CLASS = "software.amazon.event.ruler.MatchType";

    private static final String VALUE_PATTERNS_CLASS = "software.amazon.event.ruler.ValuePatterns";

    /**
     * Invokes Patterns.wildcardMatch(String) reflectively and returns the created instance.
     */
    private Object invokeWildcardMatch(String value) throws Exception {
        Class<?> patternsCls = Class.forName(PATTERNS_CLASS);
        Method m = patternsCls.getMethod("wildcardMatch", String.class);
        return m.invoke(null, value);
    }

    /**
     * Tries to find a declared field on the given object whose value equals expectedValue.
     * Returns true if found, false otherwise.
     */
    private boolean hasFieldWithValue(Object obj, Object expectedValue) throws Exception {
        Class<?> cls = obj.getClass();
        for (Field f : cls.getDeclaredFields()) {
            f.setAccessible(true);
            Object v = f.get(obj);
            if (expectedValue == null) {
                if (v == null) {
                    return true;
                }
            } else {
                if (expectedValue.equals(v)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Tries to find a declared field on the given object whose value is the enum constant named enumConstantName
     * of enumClassName. Returns true if found, false otherwise.
     */
    private boolean hasEnumFieldWithConstant(Object obj, String enumClassName, String enumConstantName) throws Exception {
        Class<?> cls = obj.getClass();
        Class<?> enumCls = Class.forName(enumClassName);
        Object enumConstant;
        try {
            @SuppressWarnings("unchecked")
            Class<? extends Enum> enumAsEnumClass = (Class<? extends Enum>) enumCls;
            enumConstant = Enum.valueOf(enumAsEnumClass, enumConstantName);
        } catch (IllegalArgumentException iae) {
            // enum constant does not exist
            return false;
        }
        for (Field f : cls.getDeclaredFields()) {
            f.setAccessible(true);
            Object v = f.get(obj);
            if (v != null && v.equals(enumConstant)) {
                return true;
            }
            // if underlying field stored enum as a String of its name
            if (v instanceof String && enumConstantName.equals(v)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testWildcardMatchCreatesValuePatternsAndPreservesValue() throws Exception {
        String input = "some*pattern?";
        Object vp = invokeWildcardMatch(input);
        assertNotNull(vp, "wildcardMatch should not return null");
        // The returned object's class should be the ValuePatterns class (by name)
        assertEquals(VALUE_PATTERNS_CLASS, vp.getClass().getName(), "Returned instance should be of type ValuePatterns");
        // Verify that the provided string value is stored somewhere in the created object
        boolean containsValue = hasFieldWithValue(vp, input);
        assertTrue(containsValue, "ValuePatterns instance should contain the provided value string in a field");
    }

    @Test
    public void testWildcardMatchUsesMatchTypeWildcard() throws Exception {
        String input = "abc";
        Object vp = invokeWildcardMatch(input);
        assertNotNull(vp, "wildcardMatch should not return null");
        // If MatchType.WILDCARD exists, assert that the returned ValuePatterns contains that enum constant.
        Class<?> matchTypeCls;
        try {
            matchTypeCls = Class.forName(MATCH_TYPE_CLASS);
        } catch (ClassNotFoundException e) {
            // If MatchType is not present, skip this assertion (the presence of MatchType is required for this check)
            Assumptions.assumeTrue(false, "MatchType class not present; skipping MatchType.WILDCARD assertion");
            return;
        }
        boolean wildcardExists;
        try {
            @SuppressWarnings("unchecked")
            Class<? extends Enum> enumAsEnumClass = (Class<? extends Enum>) matchTypeCls;
            Enum.valueOf(enumAsEnumClass, "WILDCARD");
            wildcardExists = true;
        } catch (IllegalArgumentException iae) {
            wildcardExists = false;
        }
        Assumptions.assumeTrue(wildcardExists, "MatchType.WILDCARD does not exist; skipping MatchType.WILDCARD assertion");
        boolean hasWildcard = hasEnumFieldWithConstant(vp, MATCH_TYPE_CLASS, "WILDCARD");
        assertTrue(hasWildcard, "ValuePatterns instance should contain MatchType.WILDCARD in one of its fields");
    }

    @Test
    public void testWildcardMatchWithNullValueCreatesInstanceAndStoresNull() throws Exception {
        Object vp = invokeWildcardMatch(null);
        assertNotNull(vp, "wildcardMatch(null) should still return a ValuePatterns instance");
        // The returned object's class should be ValuePatterns
        assertEquals(VALUE_PATTERNS_CLASS, vp.getClass().getName(), "Returned instance should be of type ValuePatterns");
        // There should be at least one String field that is null (indicating the passed null was stored)
        boolean hasNullStringField = false;
        for (Field f : vp.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (f.getType() == String.class) {
                Object v = f.get(vp);
                if (v == null) {
                    hasNullStringField = true;
                    break;
                }
            }
        }
        // We don't fail the test if no String field exists at all; just assert that if a String field exists, one is null
        // To be strict, require that at least one String field exists and is null.
        boolean hasStringField = false;
        for (Field f : vp.getClass().getDeclaredFields()) {
            if (f.getType() == String.class) {
                hasStringField = true;
                break;
            }
        }
        if (hasStringField) {
            assertTrue(hasNullStringField, "If ValuePatterns has a String field, it should be null when created with null value");
        }
    }
}
