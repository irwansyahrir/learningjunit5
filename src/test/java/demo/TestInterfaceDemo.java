package demo;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("demo")
class TestInterfaceDemo implements TestLifecycleLogger,
        TimeExecutionLogger, TestInterfaceDynamicTestsDemo {

    //TODO: explore more use cases here. So far reporting has been the strong feature of JUnit5
    //Can it be more meaningful for TDD? Readable test report while
    //Or the reporting as documentation

    @Test
    void isEqualValue() {
        assertEquals(1, 1, "is always equal");
    }

}