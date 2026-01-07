package software.amazon.event.ruler.input;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static software.amazon.event.ruler.input.InputCharacterType.WILDCARD;

public class InputWildcard_equals_1_0_Test_equals_DifferentClass_ReturnsFalse {




    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        InputWildcard wildcard = new InputWildcard();
        Object other = new Object();
        assertFalse(wildcard.equals(other), "equals should return false when compared to a different class instance");
    }



    // A simple subclass to test class-difference behavior of equals
    static class InputWildcardSub extends InputWildcard {
        // no additional behavior required
    }
}
