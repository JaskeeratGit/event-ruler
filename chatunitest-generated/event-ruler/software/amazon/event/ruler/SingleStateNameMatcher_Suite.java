package software.amazon.event.ruler;

import org.junit.runner.RunWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;

@RunWith(value = JUnitPlatform.class)
@SelectClasses(value = { SingleStateNameMatcher_findPattern_3_0_Test.class, SingleStateNameMatcher_addPattern_1_0_Test.class, SingleStateNameMatcher_deletePattern_2_0_Test.class })
public class SingleStateNameMatcher_Suite {
}
