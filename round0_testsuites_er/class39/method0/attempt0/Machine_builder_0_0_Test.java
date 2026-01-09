package software.amazon.event.ruler;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

class Machine_builder_0_0_Test {

    @Test
    void testBuilderReturnsNonNullBuilderWithExpectedName() {
        Object builder = Machine.builder();
        assertNotNull(builder, "builder() should not return null");
        // The returned object should be an inner Builder class; check its simple name.
        assertEquals("Builder", builder.getClass().getSimpleName(), "Returned object's class simple name should be 'Builder'");
    }

    @Test
    void testBuilderReturnsNewInstanceOnEachCall() {
        Object b1 = Machine.builder();
        Object b2 = Machine.builder();
        assertNotNull(b1);
        assertNotNull(b2);
        assertNotSame(b1, b2, "builder() should return a new Builder instance on each call");
        assertEquals(b1.getClass(), b2.getClass(), "Both Builder instances should have the same runtime class");
    }

    @Test
    void testBuilderInvokedViaReflection() throws Exception {
        Method builderMethod = Machine.class.getDeclaredMethod("builder");
        builderMethod.setAccessible(true);
        Object builder = builderMethod.invoke(null);
        assertNotNull(builder, "Reflection-invoked builder() should not return null");
        assertEquals("Builder", builder.getClass().getSimpleName(), "Reflection-invoked object's class simple name should be 'Builder'");
    }

    @Test
    void testDeprecatedPublicNoArgConstructorAvailable() {
        // Ensure the deprecated public no-arg constructor is usable
        Machine m = new Machine();
        assertNotNull(m, "Deprecated public no-arg constructor should produce a non-null instance");
        assertEquals("Machine", m.getClass().getSimpleName());
    }
}
