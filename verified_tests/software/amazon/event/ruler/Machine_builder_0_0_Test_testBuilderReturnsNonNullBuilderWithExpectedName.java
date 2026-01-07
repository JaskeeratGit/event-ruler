package software.amazon.event.ruler;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

class Machine_builder_0_0_Test_testBuilderReturnsNonNullBuilderWithExpectedName {

    @Test
    void testBuilderReturnsNonNullBuilderWithExpectedName() {
        Object builder = Machine.builder();
        assertNotNull(builder, "builder() should not return null");
        // The returned object should be an inner Builder class; check its simple name.
        assertEquals("Builder", builder.getClass().getSimpleName(), "Returned object's class simple name should be 'Builder'");
    }



}
