package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.InputStream;

class JsonRuleCompiler_compile_14_0_Test_testCompileNullInput_throwsNullPointerException {

    @Test
    void testCompileNullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> JsonRuleCompiler.compile((InputStream) null, false));
    }

}
