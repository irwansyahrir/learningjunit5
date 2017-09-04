package first;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Tag("demo")
public class FirstTests {

    @Test
    @DisplayName("Testing 1+1 = 2")
//    @DisplayName("Same Display Name")
    void myFirstTest() {
        assertEquals(2, 1 + 1);
    }

    @Test
    @Disabled
    @DisplayName("Demonstrating fail() and @Disabled ╯°□°）╯")
//    @DisplayName("Same Display Name")
    void alwaysFails() {
        fail("I am destined to fail");
    }

    @Test
    @DisplayName("Testing a Calculator 😎")
//    @DisplayName("Same Display Name")
    void testCalculator(TestInfo testInfo) {
        Calculator calculator = new Calculator();
        assertEquals(2, calculator.add(1, 1), "1 + 1 should equal 2");
        assertEquals("Testing a Calculator 😎", testInfo.getDisplayName(), "TestInfo is injected correctly");
    }
}


