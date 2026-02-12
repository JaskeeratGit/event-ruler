package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Patterns_anythingButSuffix_17_0_Test_testAnythingButSuffix_nullSuffix_throwsNullPointerException {

    @Test
    public void testAnythingButSuffix_nullSuffix_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Patterns.anythingButSuffix((String) null), "Passing null suffix should throw NullPointerException");
    }
}
