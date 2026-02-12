package software.amazon.event.ruler;

import java.io.InputStream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonRuleCompiler_compile_14_0_Test_testCompileNullInput_throwsNullPointerException {

    @Test
    void testCompileNullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> JsonRuleCompiler.compile((InputStream) null, false));
    }

}
