package software.amazon.event.ruler;

import org.junit.runner.RunWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;

@RunWith(value = JUnitPlatform.class)
@SelectClasses(value = { RuleCompiler_compile_8_0_Test.class, RuleCompiler_compile_13_0_Test.class, RuleCompiler_compile_12_0_Test.class, RuleCompiler_compile_14_0_Test.class, RuleCompiler_compile_10_0_Test.class, RuleCompiler_compile_11_0_Test.class, RuleCompiler_compile_15_0_Test.class })
public class RuleCompiler_Suite {
}
