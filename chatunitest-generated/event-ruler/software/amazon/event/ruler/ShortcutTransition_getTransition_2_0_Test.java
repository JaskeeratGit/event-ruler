package software.amazon.event.ruler;

import java.lang.reflect.Field;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.Set;

class ShortcutTransition_getTransition_2_0_Test {

    private ShortcutTransition shortcutTransition;

    @BeforeEach
    void setUp() {
        shortcutTransition = new ShortcutTransition();
    }

    @Test
    void testGetTransitionReturnsNullForVariousBytes() {
        byte[] samples = new byte[] { 0, 1, -1, 127, -128, 10, 42 };
        for (byte b : samples) {
            assertNull(shortcutTransition.getTransition(b), "Expected null transition for byte: " + b);
        }
    }

    @Test
    void testPrivateMatchFieldIsAccessibleAndNull_thenGetTransitionStillReturnsNull() throws Exception {
        // Access private field 'match' via reflection
        Field matchField = ShortcutTransition.class.getDeclaredField("match");
        matchField.setAccessible(true);
        // Initially the private field should be null (per provided class)
        Object initialMatch = matchField.get(shortcutTransition);
        assertNull(initialMatch, "Expected initial private field 'match' to be null");
        // Invoke getTransition and assert it returns null
        byte testByte = 55;
        assertNull(shortcutTransition.getTransition(testByte), "Expected null transition for byte: " + testByte);
        // Explicitly set the private field to null again (no-op but verifies we can set it)
        matchField.set(shortcutTransition, null);
        Object afterSet = matchField.get(shortcutTransition);
        assertNull(afterSet, "Expected private field 'match' to remain null after setting it via reflection");
        // Re-check getTransition
        assertNull(shortcutTransition.getTransition(testByte), "Expected null transition after reflective field manipulation");
    }
}
