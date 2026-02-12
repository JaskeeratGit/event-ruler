package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.InputStream;

class RuleCompiler_compile_14_0_Test_compile_withNullInput_shouldThrowNullPointerException {

    @Test
    void compile_withNullInput_shouldThrowNullPointerException() {
        // Passing null should result in a NullPointerException when the factory attempts to create a parser.
        assertThrows(NullPointerException.class, () -> RuleCompiler.compile((InputStream) null, true));
    }

}
