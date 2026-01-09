package software.amazon.event.ruler.input;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static software.amazon.event.ruler.input.InputCharacterType.WILDCARD;

public class InputWildcard_equals_1_0_Test {

    @Test
    public void equals_Null_ReturnsFalse() {
        InputWildcard wildcard = new InputWildcard();
        assertFalse(wildcard.equals(null), "equals should return false when compared to null");
    }

    @Test
    public void equals_SameInstance_ReturnsTrue() {
        InputWildcard wildcard = new InputWildcard();
        assertTrue(wildcard.equals(wildcard), "equals should be reflexive for the same instance");
    }

    @Test
    public void equals_DifferentInstanceSameClass_ReturnsTrue() {
        InputWildcard w1 = new InputWildcard();
        InputWildcard w2 = new InputWildcard();
        assertTrue(w1.equals(w2), "different instances of same concrete class should be equal");
        assertTrue(w2.equals(w1), "symmetry: different instances of same concrete class should be equal");
    }

    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        InputWildcard wildcard = new InputWildcard();
        Object other = new Object();
        assertFalse(wildcard.equals(other), "equals should return false when compared to a different class instance");
    }

    @Test
    public void equals_SubclassInstance_ReturnsFalse() {
        InputWildcard parent = new InputWildcard();
        InputWildcardSub child = new InputWildcardSub();
        // parent.equals(child) should be false because classes differ
        assertFalse(parent.equals(child), "equals should return false when compared to a subclass instance");
        // child.equals(parent) should also be false (inherited equals checks getClass())
        assertFalse(child.equals(parent), "equals should return false when subclass instance compared to parent instance");
    }

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
