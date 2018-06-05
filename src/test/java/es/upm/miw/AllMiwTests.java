package es.upm.miw;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.upm.miw.businessControllers.AllMiwBusinessControllersIntegrationTests;
import es.upm.miw.businessControllers.AllMiwUtilsTests;
import es.upm.miw.dataServices.AllMiwDataServicesIntegrationTests;
import es.upm.miw.documents.core.AllMiwDocumentsCoreTests;
import es.upm.miw.repositories.core.AllMiwRepositoriesCoreIntegrationTests;
import es.upm.miw.restControllers.AllMiwResourcesFunctionalTests;

@RunWith(Suite.class)
@SuiteClasses({
        AllMiwDocumentsCoreTests.class,
        AllMiwUtilsTests.class,

        AllMiwBusinessControllersIntegrationTests.class,
        AllMiwRepositoriesCoreIntegrationTests.class,
        AllMiwDataServicesIntegrationTests.class,

        AllMiwResourcesFunctionalTests.class,
})
public class AllMiwTests {
}
