package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RuleCompiler_compile_14_0_Test_compile_withNullInput_shouldThrowNullPointerException {

    @Test
    void compile_withNullInput_shouldThrowNullPointerException() {
        // Disambiguate overloaded compile by casting null to InputStream.
        assertThrows(NullPointerException.class, () -> RuleCompiler.compile((InputStream) null, true));
    }

}
