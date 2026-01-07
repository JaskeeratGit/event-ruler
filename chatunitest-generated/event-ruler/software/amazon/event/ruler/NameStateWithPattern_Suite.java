package software.amazon.event.ruler;

import org.junit.runner.RunWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;

@RunWith(value = JUnitPlatform.class)
@SelectClasses(value = { NameStateWithPattern_equals_2_0_Test.class, NameStateWithPattern_hashCode_3_0_Test.class })
public class NameStateWithPattern_Suite {
}
