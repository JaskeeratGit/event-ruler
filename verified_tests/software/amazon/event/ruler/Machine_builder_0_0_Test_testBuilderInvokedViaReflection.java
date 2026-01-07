package software.amazon.event.ruler;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

class Machine_builder_0_0_Test_testBuilderInvokedViaReflection {



    @Test
    void testBuilderInvokedViaReflection() throws Exception {
        Method builderMethod = Machine.class.getDeclaredMethod("builder");
        builderMethod.setAccessible(true);
        Object builder = builderMethod.invoke(null);
        assertNotNull(builder, "Reflection-invoked builder() should not return null");
        assertEquals("Builder", builder.getClass().getSimpleName(), "Reflection-invoked object's class simple name should be 'Builder'");
    }

}
