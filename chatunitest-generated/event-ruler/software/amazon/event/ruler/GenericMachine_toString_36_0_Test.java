package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import static software.amazon.event.ruler.SetOperations.intersection;

/**
 * Unit tests for GenericMachine#toString()
 *
 * These tests use reflection to access and mutate private fields to ensure predictable
 * toString() output parts without requiring full knowledge of NameState's toString().
 */
public class GenericMachine_toString_36_0_Test {

    @Test
    public void toString_containsExpectedStructure_whenEmptyFieldStepsUsedRefCount() throws Exception {
        // Arrange
        GenericMachine<Object> gm = new GenericMachine<>();
        // Act
        String s = gm.toString();
        // Assert basic structure: begins with class marker and contains empty map representation
        assertTrue(s.startsWith("GenericMachine{startState="), "toString should start with GenericMachine{startState=");
        assertTrue(s.contains("fieldStepsUsedRefCount={}") || s.contains("fieldStepsUsedRefCount={"), "toString should contain fieldStepsUsedRefCount map representation");
        assertTrue(s.endsWith("}"), "toString should end with a closing brace");
    }

    @Test
    public void toString_reflectsMutatedFieldStepsUsedRefCount() throws Exception {
        // Arrange
        GenericMachine<Object> gm = new GenericMachine<>();
        // Use reflection to access the private final fieldStepsUsedRefCount map and mutate it
        Field mapField = GenericMachine.class.getDeclaredField("fieldStepsUsedRefCount");
        mapField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, Integer> map = (Map<String, Integer>) mapField.get(gm);
        // Precondition: ensure map is empty for deterministic test
        map.clear();
        // Act: add an entry and get toString
        map.put("a", 1);
        String sAfterPut = gm.toString();
        // Assert: the string must contain the inserted mapping and maintain the expected structural pieces
        assertTrue(sAfterPut.startsWith("GenericMachine{startState="), "toString should start with GenericMachine{startState=");
        assertTrue(sAfterPut.contains("fieldStepsUsedRefCount={a=1}"), "toString should contain the inserted map entry 'a=1'");
        assertTrue(sAfterPut.endsWith("}"), "toString should end with a closing brace");
        // Act: mutate again (change value)
        map.put("a", 42);
        String sAfterChange = gm.toString();
        // Assert: new value reflected
        assertTrue(sAfterChange.contains("fieldStepsUsedRefCount={a=42}"), "toString should reflect updated map value 'a=42'");
        // Act: clear map
        map.clear();
        String sAfterClear = gm.toString();
        // Assert: cleared map reflected
        assertTrue(sAfterClear.contains("fieldStepsUsedRefCount={}"), "toString should reflect cleared map as {}");
    }
}
