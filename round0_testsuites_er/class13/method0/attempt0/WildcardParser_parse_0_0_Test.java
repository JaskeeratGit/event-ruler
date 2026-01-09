package software.amazon.event.ruler.input;

import java.lang.reflect.Field;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import static software.amazon.event.ruler.input.DefaultParser.ASTERISK_BYTE;
import static software.amazon.event.ruler.input.DefaultParser.BACKSLASH_BYTE;

public class WildcardParser_parse_0_0_Test {

    private final WildcardParser parser = new WildcardParser();

    // Helper to extract the first byte-typed field value from an InputByte instance via reflection
    private byte extractByteFromInputByte(Object inputByteObj) {
        Class<?> cls = inputByteObj.getClass();
        while (cls != null) {
            for (Field f : cls.getDeclaredFields()) {
                Class<?> ft = f.getType();
                if (ft == byte.class || ft == Byte.class) {
                    f.setAccessible(true);
                    try {
                        Object val = f.get(inputByteObj);
                        if (val instanceof Byte) {
                            return ((Byte) val).byteValue();
                        } else if (val instanceof Number) {
                            return ((Number) val).byteValue();
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            cls = cls.getSuperclass();
        }
        throw new IllegalStateException("No byte field found on InputByte instance");
    }

    @Test
    public void testParsePlainBytes() {
        InputCharacter[] res = parser.parse("abc");
        assertEquals(3, res.length);
        assertTrue(res[0] instanceof InputByte);
        assertTrue(res[1] instanceof InputByte);
        assertTrue(res[2] instanceof InputByte);
        assertEquals((byte) 'a', extractByteFromInputByte(res[0]));
        assertEquals((byte) 'b', extractByteFromInputByte(res[1]));
        assertEquals((byte) 'c', extractByteFromInputByte(res[2]));
    }

    @Test
    public void testParseSingleWildcard() {
        InputCharacter[] res = parser.parse("*");
        assertEquals(1, res.length);
        assertTrue(res[0] instanceof InputWildcard);
    }

    @Test
    public void testParseEscapedAsterisk() {
        // Java string "\\*" -> characters: backslash, asterisk
        InputCharacter[] res = parser.parse("\\*");
        assertEquals(1, res.length);
        assertTrue(res[0] instanceof InputByte);
        assertEquals(DefaultParser.ASTERISK_BYTE, extractByteFromInputByte(res[0]));
    }

    @Test
    public void testParseEscapedBackslash() {
        // Java string "\\\\" -> characters: backslash, backslash
        InputCharacter[] res = parser.parse("\\\\");
        assertEquals(1, res.length);
        assertTrue(res[0] instanceof InputByte);
        assertEquals(DefaultParser.BACKSLASH_BYTE, extractByteFromInputByte(res[0]));
    }

    @Test
    public void testMixedSequenceWithEscapedAsterisk() {
        // "a\\*b" -> characters: 'a', backslash, '*', 'b' -> should become [a, '*' , b]
        InputCharacter[] res = parser.parse("a\\*b");
        assertEquals(3, res.length);
        assertTrue(res[0] instanceof InputByte);
        assertTrue(res[1] instanceof InputByte);
        assertTrue(res[2] instanceof InputByte);
        assertEquals((byte) 'a', extractByteFromInputByte(res[0]));
        assertEquals(DefaultParser.ASTERISK_BYTE, extractByteFromInputByte(res[1]));
        assertEquals((byte) 'b', extractByteFromInputByte(res[2]));
    }

    @Test
    public void testConsecutiveWildcardsThrows() {
        ParseException ex = assertThrows(ParseException.class, () -> parser.parse("**"));
        assertTrue(ex.getMessage().contains("pos 0"));
    }

    @Test
    public void testInvalidEscapeAtEndThrows() {
        ParseException ex = assertThrows(ParseException.class, () -> parser.parse("\\"));
        assertTrue(ex.getMessage().contains("pos 0"));
    }

    @Test
    public void testInvalidEscapeNonEscapedNextThrows() {
        ParseException ex = assertThrows(ParseException.class, () -> parser.parse("\\a"));
        assertTrue(ex.getMessage().contains("pos 0"));
    }
}
