package software.amazon.event.ruler;

import org.junit.runner.RunWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;

@RunWith(value = JUnitPlatform.class)
@SelectClasses(value = { ValuePatterns_hashCode_2_0_Test.class, ValuePatterns_equals_1_0_Test.class, ValuePatterns_toString_3_0_Test.class })
public class ValuePatterns_Suite {
}
