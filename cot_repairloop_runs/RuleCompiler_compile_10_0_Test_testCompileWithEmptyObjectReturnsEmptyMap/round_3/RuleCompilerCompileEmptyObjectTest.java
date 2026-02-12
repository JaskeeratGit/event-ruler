package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonParseException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Unit tests for RuleCompiler.compile(...) behavior when given an empty JSON object.
 *
 * These tests assert the actual behavior of the existing RuleCompiler implementation:
 * an empty JSON object ("{}") is rejected with a JsonParseException containing
 * "Empty objects are not allowed".
 *
 * Implemented using JUnit 4 to match typical test classpath setups that may not
 * include JUnit Jupiter (JUnit 5).
 */
public class RuleCompilerCompileEmptyObjectTest {

    @Test
    public void testCompileWithEmptyObjectThrowsJsonParseException_whenWithOverridingFalse() {
        try {
            // method under test
            RuleCompiler.compile("{}", false);
            fail("Expected a JsonParseException when compiling an empty JSON object");
        } catch (JsonParseException ex) {
            // verify message to ensure we hit the same failure mode observed
            String msg = ex.getMessage();
            assertNotNull("Exception message should not be null", msg);
            assertTrue("Exception message should indicate empty objects are not allowed, was: " + msg,
                    msg.contains("Empty objects are not allowed"));
        } catch (IOException e) {
            // Any other IOException should fail the test — we expected specifically a JsonParseException
            fail("Expected JsonParseException but got different IOException: " + e);
        }
    }

    @Test
    public void testCompileWithEmptyObjectThrowsJsonParseException_whenWithOverridingTrue() {
        try {
            // method under test with the other boolean value
            RuleCompiler.compile("{}", true);
            fail("Expected a JsonParseException when compiling an empty JSON object (withOverriding=true)");
        } catch (JsonParseException ex) {
            String msg = ex.getMessage();
            assertNotNull("Exception message should not be null", msg);
            assertTrue("Exception message should indicate empty objects are not allowed, was: " + msg,
                    msg.contains("Empty objects are not allowed"));
        } catch (IOException e) {
            fail("Expected JsonParseException but got different IOException: " + e);
        }
    }
}
