package software.amazon.event.ruler;

import java.io.InputStream;
import org.junit.Test;

public class JsonRuleCompiler_compile_14_0_Test_testCompileNullInput_throwsNullPointerException {

    @Test
    public void testCompileNullInput_throwsNullPointerException() throws Exception {
        try {
            JsonRuleCompiler.compile((InputStream) null, false);
            org.junit.Assert.fail("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

}
