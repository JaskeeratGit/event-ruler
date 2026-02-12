package software.amazon.event.ruler;

import org.junit.Test;
import com.fasterxml.jackson.core.JsonParseException;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RuleCompiler_compile_10_0_Test_testCompileWithEmptyObjectReturnsEmptyMap {

    @Test
    public void testCompileWithEmptyObjectThrowsJsonParseException() throws Exception {
        try {
            RuleCompiler.compile("{}", false);
            fail("Expected JsonParseException for empty object");
        } catch (JsonParseException e) {
            assertTrue("Exception message should indicate empty objects are not allowed",
                       e.getMessage() != null && e.getMessage().contains("Empty objects are not allowed"));
        }
    }

}
