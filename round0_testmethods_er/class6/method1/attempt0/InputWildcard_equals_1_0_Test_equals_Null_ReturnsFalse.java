package software.amazon.event.ruler.input;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static software.amazon.event.ruler.input.InputCharacterType.WILDCARD;

public class InputWildcard_equals_1_0_Test_equals_Null_ReturnsFalse {

    @Test
    public void equals_Null_ReturnsFalse() {
        InputWildcard wildcard = new InputWildcard();
        assertFalse(wildcard.equals(null), "equals should return false when compared to null");
    }






    // A simple subclass to test class-difference behavior of equals
    static class InputWildcardSub extends InputWildcard {
        // no additional behavior required
    }
}
