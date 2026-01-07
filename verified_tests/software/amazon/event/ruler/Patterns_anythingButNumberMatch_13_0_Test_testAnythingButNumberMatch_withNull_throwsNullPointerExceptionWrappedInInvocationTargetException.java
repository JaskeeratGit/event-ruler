package software.amazon.event.ruler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.stream.Collectors;

class Patterns_anythingButNumberMatch_13_0_Test_testAnythingButNumberMatch_withNull_throwsNullPointerExceptionWrappedInInvocationTargetException {



    @Test
    void testAnythingButNumberMatch_withNull_throwsNullPointerExceptionWrappedInInvocationTargetException() throws Exception {
        Method publicMethod = Patterns.class.getDeclaredMethod("anythingButNumberMatch", Set.class);
        publicMethod.setAccessible(true);
        try {
            // invoking with null should cause the stream operation inside the method to throw a NullPointerException,
            // which will be wrapped in InvocationTargetException by Method.invoke
            publicMethod.invoke(null, (Object) null);
            fail("Expected InvocationTargetException when invoking with null");
        } catch (InvocationTargetException ite) {
            assertNotNull(ite.getCause(), "InvocationTargetException should have a cause");
            assertTrue(ite.getCause() instanceof NullPointerException, "Cause of InvocationTargetException should be a NullPointerException when input is null");
        }
    }
}
