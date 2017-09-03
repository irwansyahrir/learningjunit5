package demo;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("demo")
@DisplayName("TestInfo Demo")
public class TestInfoDemo {

    //TODO: find use cases for this feature. 1. Use it together with TestReporter. 2. Logging

    TestInfoDemo(TestInfo testInfo) {
        assertEquals("TestInfo Demo", testInfo.getDisplayName());
    }

    @BeforeEach
    void init(TestInfo testInfo) {
        String displayName = testInfo.getDisplayName();
        assertTrue(displayName.equals("TEST 1") || displayName.equals("test2()"));
    }

    @Test
    @DisplayName("TEST 1")
    @Tag("my-tag")
    void test1(TestInfo testInfo) {
        assertEquals("TEST 1", testInfo.getDisplayName());
        assertTrue(testInfo.getTags().contains("my-tag"));
        assertEquals("demo.TestInfoDemo", testInfo.getTestClass().get().getName());
        assertEquals("test1", testInfo.getTestMethod().get().getName());
    }

    @Test
    void test2() {
    }
}
