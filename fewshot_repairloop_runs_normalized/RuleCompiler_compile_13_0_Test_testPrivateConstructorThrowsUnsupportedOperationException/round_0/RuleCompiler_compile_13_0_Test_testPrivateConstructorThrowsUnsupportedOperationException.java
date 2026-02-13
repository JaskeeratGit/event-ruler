package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class RuleCompiler_compile_13_0_Test_testPrivateConstructorThrowsUnsupportedOperationException {

    @Test
    public void testPrivateConstructorThrowsUnsupportedOperationException() throws Exception {
        Constructor<RuleCompiler> ctor = RuleCompiler.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(ctor.getModifiers()), "Expected constructor to be private");
    }
}
