package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RuleCompiler_compile_11_0_Test_compileWithNullSourceThrowsNullPointerException {

    @Test
    void compileWithNullSourceThrowsNullPointerException() {
        // JsonFactory.createParser(null) is expected to throw NullPointerException
        assertThrows(NullPointerException.class, () -> RuleCompiler.compile((String) null));
    }
}
