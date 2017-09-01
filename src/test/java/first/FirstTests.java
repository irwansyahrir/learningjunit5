package first;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FirstTests {

    @Test
    @DisplayName("Testing 1+1 = 2")
    void myFirstTest() {
        assertEquals(2, 1 + 1);
    }

    @Test
    @Disabled
    @DisplayName("Demonstrating fail() and @Disabled ╯°□°）╯")
    void alwaysFails() {
        fail("I am destined to fail");
    }

    @Test
    @DisplayName("Testing a Calculator 😎")
    void testCalculator(TestInfo testInfo) {
        Calculator calculator = new Calculator();
        assertEquals(2, calculator.add(1, 1), "1 + 1 should equal 2");
        assertEquals("Testing a Calculator 😎", testInfo.getDisplayName(), "TestInfo is injected correctly");
    }
}


