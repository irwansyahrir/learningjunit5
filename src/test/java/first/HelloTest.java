package first;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class HelloTest {

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
}


