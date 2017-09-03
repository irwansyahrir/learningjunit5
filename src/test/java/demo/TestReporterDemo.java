package demo;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;

import java.util.HashMap;

@Tag("demo")
public class TestReporterDemo {

    @Test
    void reportSingleValue(TestReporter testReporter) {
        testReporter.publishEntry("a key", "a value");
    }

    @Test
    void reportSeveralValues(TestReporter testReporter) {
        HashMap<String, String> values = new HashMap<>();
        values.put("user name", "dk38");
        values.put("award year", "1974");

        testReporter.publishEntry(values);
    }

    //TODO: this looks more useful. Combined with TestInfo??
    @Test
    void reportSeveralValuesIncludingFromTestInfo(TestReporter testReporter, TestInfo testInfo) {
        HashMap<String, String> values = new HashMap<>();
        values.put("user name", "userName");
        values.put("award year", "2017");
        values.put("Test class name", testInfo.getTestClass().get().getName());

        testReporter.publishEntry(values);

    }
}
