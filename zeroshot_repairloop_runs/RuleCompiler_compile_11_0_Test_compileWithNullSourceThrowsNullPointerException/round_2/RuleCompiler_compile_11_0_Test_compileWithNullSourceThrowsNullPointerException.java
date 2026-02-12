package software.amazon.event.ruler;

import org.junit.Test;

public class RuleCompiler_compile_11_0_Test_compileWithNullSourceThrowsNullPointerException {

    @Test(expected = NullPointerException.class)
    public void compileWithNullSourceThrowsNullPointerException() throws Exception {
        // Ensure we call the String overload explicitly to avoid ambiguity between overloads.
        RuleCompiler.compile((String) null);
    }
}
