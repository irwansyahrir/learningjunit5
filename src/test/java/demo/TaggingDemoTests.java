package demo;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Tag("fast")
@Tag("model")
public class TaggingDemoTests {

    //TODO: how does this work? what use cases?

    @Test
    @Tag("taxes")
    void testingTaxCalculation() {
        assertEquals(1, 1);
    }
}
