package software.amazon.event.ruler.input;

import org.junit.runner.RunWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;

@RunWith(value = JUnitPlatform.class)
@SelectClasses(value = { MultiByte_is_2_0_Test.class, MultiByte_hashCode_10_0_Test.class, MultiByte_isLessThanOrEqualTo_5_0_Test.class, MultiByte_singular_1_0_Test.class })
public class MultiByte_Suite {
}
