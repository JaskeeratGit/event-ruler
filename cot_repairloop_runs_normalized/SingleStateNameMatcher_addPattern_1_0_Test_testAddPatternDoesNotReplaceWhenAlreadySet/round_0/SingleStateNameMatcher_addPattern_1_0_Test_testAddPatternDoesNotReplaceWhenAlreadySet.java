package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.annotation.Nonnull;

class SingleStateNameMatcher_addPattern_1_0_Test_testAddPatternDoesNotReplaceWhenAlreadySet {

    // Helper to create an instance of a class by fully qualified name using its no-arg constructor
    private Object newInstanceNoArg(String fqcn) throws Exception {
        Class<?> cls = Class.forName(fqcn);
        Constructor<?> ctor = cls.getDeclaredConstructor();
        ctor.setAccessible(true);
        return ctor.newInstance();
    }

    // Helper to create a Patterns instance using its declared constructor (passes null for its parameter)
    private Object newPatternsInstance() throws Exception {
        Class<?> patternsClass = Class.forName("software.amazon.event.ruler.Patterns");
        Constructor<?>[] ctors = patternsClass.getDeclaredConstructors();
        // choose a constructor and call it with nulls (constructor expects MatchType, but null is acceptable for test)
        Constructor<?> ctor = ctors[0];
        ctor.setAccessible(true);
        // create with a single null parameter or no parameters depending on constructor arity
        Object[] params = new Object[ctor.getParameterCount()];
        for (int i = 0; i < params.length; i++) params[i] = null;
        return ctor.newInstance((Object[]) params);
    }

    // Helper to create a NameState instance (no-arg constructor expected)
    private Object newNameStateInstance() throws Exception {
        return newInstanceNoArg("software.amazon.event.ruler.NameState");
    }

    // Helper to get private field 'nameState' from SingleStateNameMatcher
    private Object getInternalNameState(Object matcher) throws Exception {
        Field f = matcher.getClass().getDeclaredField("nameState");
        f.setAccessible(true);
        return f.get(matcher);
    }

    // Helper to set private field 'nameState' on SingleStateNameMatcher
    private void setInternalNameState(Object matcher, Object nameState) throws Exception {
        Field f = matcher.getClass().getDeclaredField("nameState");
        f.setAccessible(true);
        f.set(matcher, nameState);
    }


    @Test
    void testAddPatternDoesNotReplaceWhenAlreadySet() throws Exception {
        // Arrange
        Class<?> matcherClass = Class.forName("software.amazon.event.ruler.SingleStateNameMatcher");
        Object matcher = matcherClass.getDeclaredConstructor().newInstance();
        Object initialNameState = newNameStateInstance();
        // Pre-set internal nameState to simulate already set
        setInternalNameState(matcher, initialNameState);
        Object patterns = newPatternsInstance();
        Object newNameState = newNameStateInstance();
        // Act: invoke addPattern with a different NameState
        Class<?> patternsClass = Class.forName("software.amazon.event.ruler.Patterns");
        Class<?> nameStateClass = Class.forName("software.amazon.event.ruler.NameState");
        Method addPatternMethod = matcherClass.getDeclaredMethod("addPattern", patternsClass, nameStateClass);
        addPatternMethod.setAccessible(true);
        Object returned = addPatternMethod.invoke(matcher, patterns, newNameState);
        // Assert: returned value should be the initially set NameState and internal field unchanged
        assertSame(initialNameState, returned, "addPattern should return the already set NameState when present");
        Object after = getInternalNameState(matcher);
        assertSame(initialNameState, after, "Internal nameState should remain the initially set NameState");
    }
}
