package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RuleCompiler_compile_11_0_Test_compileWithNullSourceThrowsNullPointerException {

    @Test
    void compileWithNullSourceThrowsNullPointerException() {
        // Cast null to String so the compile(String) overload is selected (otherwise compile(null)
        // is ambiguous when multiple overloads accept reference types).
        assertThrows(NullPointerException.class, () -> RuleCompiler.compile((String) null));
    }
}
