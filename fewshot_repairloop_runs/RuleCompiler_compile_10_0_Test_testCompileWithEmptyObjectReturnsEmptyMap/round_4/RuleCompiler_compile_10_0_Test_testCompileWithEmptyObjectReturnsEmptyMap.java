package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonParseException;
import org.junit.Test;

public class RuleCompiler_compile_10_0_Test_testCompileWithEmptyObjectReturnsEmptyMap {

    @Test(expected = JsonParseException.class)
    public void testCompileWithEmptyObjectReturnsEmptyMap() throws Exception {
        RuleCompiler.compile("{}", false);
    }

}
