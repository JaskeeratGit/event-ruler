package software.amazon.event.ruler.input;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static software.amazon.event.ruler.input.InputCharacterType.WILDCARD;

public class InputWildcard_equals_1_0_Test_equals_DifferentInstanceSameClass_ReturnsTrue {



    @Test
    public void equals_DifferentInstanceSameClass_ReturnsTrue() {
        InputWildcard w1 = new InputWildcard();
        InputWildcard w2 = new InputWildcard();
        assertTrue(w1.equals(w2), "different instances of same concrete class should be equal");
        assertTrue(w2.equals(w1), "symmetry: different instances of same concrete class should be equal");
    }




    // A simple subclass to test class-difference behavior of equals
    static class InputWildcardSub extends InputWildcard {
        // no additional behavior required
    }
}
