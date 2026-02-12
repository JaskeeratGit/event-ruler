package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RuleCompiler_compile_11_0_Test_compileWithNullSourceThrowsNullPointerException {

    @Test
    void compileWithNullSourceThrowsNullPointerException() {
        // Ensure we call the String overload explicitly to avoid ambiguity between overloads.
        assertThrows(NullPointerException.class, () -> RuleCompiler.compile((String) null));
    }
}
