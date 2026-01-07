package software.amazon.event.ruler.input;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.event.ruler.MatchType;
import java.nio.charset.StandardCharsets;
import static software.amazon.event.ruler.MatchType.ANYTHING_BUT_IGNORE_CASE;
import static software.amazon.event.ruler.MatchType.ANYTHING_BUT_SUFFIX;
import static software.amazon.event.ruler.MatchType.ANYTHING_BUT_WILDCARD;
import static software.amazon.event.ruler.MatchType.EQUALS_IGNORE_CASE;
import static software.amazon.event.ruler.MatchType.PREFIX_EQUALS_IGNORE_CASE;
import static software.amazon.event.ruler.MatchType.SUFFIX;
import static software.amazon.event.ruler.MatchType.SUFFIX_EQUALS_IGNORE_CASE;
import static software.amazon.event.ruler.MatchType.WILDCARD;

/**
 * Generated tests for DefaultParser.parse(byte)
 */
class DefaultParser_parse_3_0_Test_testParseOnSingletonField {



    @Test
    void testParseOnSingletonField() throws Exception {
        // Access the private static SINGLETON field reflectively
        Field singleField = DefaultParser.class.getDeclaredField("SINGLETON");
        singleField.setAccessible(true);
        // Ensure it's static final as in the production code
        int mods = singleField.getModifiers();
        assertTrue(Modifier.isStatic(mods), "SINGLETON should be static");
        // Note: final check is optional; field may be final
        DefaultParser singleton = (DefaultParser) singleField.get(null);
        assertNotNull(singleton, "SINGLETON must not be null");
        // Call parse on a few representative bytes
        byte[] testBytes = new byte[] { DefaultParser.LEFT_PARENTHESIS_BYTE, DefaultParser.RIGHT_PARENTHESIS_BYTE, DefaultParser.PLUS_SIGN_BYTE, DefaultParser.QUESTION_MARK_BYTE };
        for (byte b : testBytes) {
            Object result = singleton.parse(b);
            assertNotNull(result);
            byte extracted = extractByteFromInputCharacter(result);
            assertEquals(b, extracted);
        }
    }

    /**
     * Attempts to extract an underlying byte value from an InputCharacter implementation reflectively.
     * Strategy:
     *  - Traverse declared fields on the class and superclasses looking for a primitive byte or Byte field.
     *  - If found, return its value.
     *  - If an int field is found that likely stores the byte value, return its low 8 bits.
     *  - If nothing is found, throw IllegalStateException to fail the test (indicates unexpected implementation).
     */
    private static byte extractByteFromInputCharacter(Object inputCharacter) throws Exception {
        Class<?> cls = inputCharacter.getClass();
        while (cls != null && cls != Object.class) {
            Field[] fields = cls.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                Class<?> t = f.getType();
                if (t == byte.class) {
                    return f.getByte(inputCharacter);
                }
                if (t == Byte.class) {
                    Byte val = (Byte) f.get(inputCharacter);
                    return val == null ? (byte) 0 : val;
                }
                if (t == int.class) {
                    // some implementations may store the byte value in an int field
                    int v = f.getInt(inputCharacter);
                    return (byte) v;
                }
                if (t == Integer.class) {
                    Integer iv = (Integer) f.get(inputCharacter);
                    return iv == null ? (byte) 0 : (byte) (iv & 0xFF);
                }
            }
            cls = cls.getSuperclass();
        }
        // If no suitable field found, fail the test with informative message
        throw new IllegalStateException("Could not find a byte-containing field in " + inputCharacter.getClass());
    }
}
