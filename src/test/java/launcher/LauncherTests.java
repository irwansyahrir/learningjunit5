package launcher;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import myusecase.PaymentMapperTest;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

public class LauncherTests {

    LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
            .selectors(
                    selectPackage("simulated"),
                    selectClass(PaymentMapperTest.class)
            )
            .filters(
                    includeClassNamePatterns(".*Tests")
            )
            .build();

    Launcher launcher = LauncherFactory.create();

//    TestPlan testPlan = launcher.discover(request);

//    TestExecutionListener listener = new SummaryGeneratingListener();
//    launcher.registerTestExecutionListeners(listener);
//
//    launcher.execute(request);

}
