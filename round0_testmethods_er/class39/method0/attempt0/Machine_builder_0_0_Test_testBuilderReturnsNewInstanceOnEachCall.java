package software.amazon.event.ruler;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

class Machine_builder_0_0_Test_testBuilderReturnsNewInstanceOnEachCall {


    @Test
    void testBuilderReturnsNewInstanceOnEachCall() {
        Object b1 = Machine.builder();
        Object b2 = Machine.builder();
        assertNotNull(b1);
        assertNotNull(b2);
        assertNotSame(b1, b2, "builder() should return a new Builder instance on each call");
        assertEquals(b1.getClass(), b2.getClass(), "Both Builder instances should have the same runtime class");
    }


}
