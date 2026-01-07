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
class DefaultParser_parse_3_0_Test_testParseUsingPrivateNoArgConstructor {

    @Test
    void testParseUsingPrivateNoArgConstructor() throws Exception {
        // Create DefaultParser via its private no-arg constructor
        Constructor<DefaultParser> noArgCtor = DefaultParser.class.getDeclaredConstructor();
        noArgCtor.setAccessible(true);
        DefaultParser parser = noArgCtor.newInstance();
        // Test a variety of byte values including ASCII, digits and special characters
        byte[] testBytes = new byte[] { // negative value
        // negative value
        DefaultParser.DOLLAR_SIGN_BYTE, // negative value
        DefaultParser.ASTERISK_BYTE, // negative value
        DefaultParser.ZERO_BYTE, // negative value
        DefaultParser.NINE_BYTE, // negative value
        DefaultParser.LEFT_SQUARE_BRACKET_BYTE, // negative value
        DefaultParser.BACKSLASH_BYTE, // negative value
        DefaultParser.CARET_BYTE, // negative value
        DefaultParser.LEFT_CURLY_BRACKET_BYTE, DefaultParser.RIGHT_CURLY_BRACKET_BYTE, (byte) 0xFF };
        for (byte b : testBytes) {
            Object result = parser.parse(b);
            assertNotNull(result, "parse should not return null for byte: " + b);
            byte extracted = extractByteFromInputCharacter(result);
            assertEquals(b, extracted, "Parsed InputCharacter should represent the same byte");
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
