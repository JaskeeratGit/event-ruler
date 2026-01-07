package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.annotation.Nonnull;

public class SingleStateNameMatcher_deletePattern_2_0_Test_deletePattern_setsNameState_toNull_whenCalled {

    @Test
    public void deletePattern_setsNameState_toNull_whenCalled() throws Exception {
        // Arrange
        SingleStateNameMatcher matcher = new SingleStateNameMatcher();
        // Act: invoke deletePattern(Patterns) via reflection (Patterns argument not used by method; pass null)
        Method deleteMethod = SingleStateNameMatcher.class.getDeclaredMethod("deletePattern", Patterns.class);
        deleteMethod.setAccessible(true);
        deleteMethod.invoke(matcher, (Object) null);
        // Assert: private field nameState should be null
        Field nameStateField = SingleStateNameMatcher.class.getDeclaredField("nameState");
        nameStateField.setAccessible(true);
        Object nameStateValue = nameStateField.get(matcher);
        assertNull(nameStateValue, "nameState should be null after deletePattern is invoked");
    }
}
