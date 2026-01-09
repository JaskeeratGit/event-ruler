package software.amazon.event.ruler.input;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static software.amazon.event.ruler.input.InputCharacterType.WILDCARD;

public class InputWildcard_equals_1_0_Test_equals_UsingReflection_BehavesIdentically {






    @Test
    public void equals_UsingReflection_BehavesIdentically() throws Exception {
        InputWildcard a = new InputWildcard();
        InputWildcard b = new InputWildcard();
        InputWildcardSub sub = new InputWildcardSub();
        Method equalsMethod = InputWildcard.class.getDeclaredMethod("equals", Object.class);
        // method is public, but use reflection to invoke as requested
        Object resSame = equalsMethod.invoke(a, a);
        Object resOtherSameClass = equalsMethod.invoke(a, b);
        Object resSubclass = equalsMethod.invoke(a, sub);
        Object resDifferent = equalsMethod.invoke(a, new Object());
        assertTrue((Boolean) resSame, "reflection: instance should equal itself");
        assertTrue((Boolean) resOtherSameClass, "reflection: different instances of same class should be equal");
        assertFalse((Boolean) resSubclass, "reflection: instance should not equal subclass instance");
        assertFalse((Boolean) resDifferent, "reflection: instance should not equal different class instance");
    }

    // A simple subclass to test class-difference behavior of equals
    static class InputWildcardSub extends InputWildcard {
        // no additional behavior required
    }
}
