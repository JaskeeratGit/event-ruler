package software.amazon.event.ruler;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

class Machine_builder_0_0_Test_testDeprecatedPublicNoArgConstructorAvailable {




    @Test
    void testDeprecatedPublicNoArgConstructorAvailable() {
        // Ensure the deprecated public no-arg constructor is usable
        Machine m = new Machine();
        assertNotNull(m, "Deprecated public no-arg constructor should produce a non-null instance");
        assertEquals("Machine", m.getClass().getSimpleName());
    }
}
