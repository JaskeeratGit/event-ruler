package software.amazon.event.ruler.input;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static software.amazon.event.ruler.input.InputCharacterType.WILDCARD;

public class InputWildcard_equals_1_0_Test_equals_SubclassInstance_ReturnsFalse {





    @Test
    public void equals_SubclassInstance_ReturnsFalse() {
        InputWildcard parent = new InputWildcard();
        InputWildcardSub child = new InputWildcardSub();
        // parent.equals(child) should be false because classes differ
        assertFalse(parent.equals(child), "equals should return false when compared to a subclass instance");
        // child.equals(parent) should also be false (inherited equals checks getClass())
        assertFalse(child.equals(parent), "equals should return false when subclass instance compared to parent instance");
    }


    // A simple subclass to test class-difference behavior of equals
    static class InputWildcardSub extends InputWildcard {
        // no additional behavior required
    }
}
