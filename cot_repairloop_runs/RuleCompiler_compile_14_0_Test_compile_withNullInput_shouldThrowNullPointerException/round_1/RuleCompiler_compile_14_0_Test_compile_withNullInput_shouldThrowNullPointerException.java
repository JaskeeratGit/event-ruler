package software.amazon.event.ruler;

import org.junit.Test;

import java.io.InputStream;

/**
 * Fixed unit test using JUnit 4 to avoid JUnit 5 (jupiter) imports that were not available on the classpath.
 */
public class RuleCompiler_compile_14_0_Test_compile_withNullInput_shouldThrowNullPointerException {

    @Test(expected = NullPointerException.class)
    public void compile_withNullInput_shouldThrowNullPointerException() throws Exception {
        // Disambiguate overloaded compile by casting null to InputStream.
        RuleCompiler.compile((InputStream) null, true);
    }

}
