package software.amazon.event.ruler;

import org.junit.runner.RunWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;

@RunWith(value = JUnitPlatform.class)
@SelectClasses(value = { SubRuleContext_equals_1_0_Test.class, SubRuleContext_hashCode_2_0_Test.class })
public class SubRuleContext_Suite {
}
