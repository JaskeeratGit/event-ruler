package software.amazon.event.ruler;

import java.lang.reflect.Field;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Patterns_exactMatch_2_0_Test_testExistsByteStringConstant {



    @Test
    public void testExistsByteStringConstant() {
        assertEquals("N", Patterns.EXISTS_BYTE_STRING, "EXISTS_BYTE_STRING constant should be 'N'");
    }
}
