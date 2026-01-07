package software.amazon.event.ruler;

import org.junit.runner.RunWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;

@RunWith(value = JUnitPlatform.class)
@SelectClasses(value = { JsonRuleCompiler_check_2_0_Test.class, JsonRuleCompiler_check_4_0_Test.class, JsonRuleCompiler_check_1_0_Test.class, JsonRuleCompiler_compile_10_0_Test.class, JsonRuleCompiler_check_5_0_Test.class, JsonRuleCompiler_compile_14_0_Test.class, JsonRuleCompiler_compile_13_0_Test.class, JsonRuleCompiler_compile_11_0_Test.class, JsonRuleCompiler_check_3_0_Test.class })
public class JsonRuleCompiler_Suite {
}
