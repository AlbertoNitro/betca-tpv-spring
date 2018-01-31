package es.upm.miw;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.upm.miw.documents.core.AllMiwDocumentsCoreTests;
import es.upm.miw.repositories.core.AllMiwRepositoriesCoreIntegrationTets;
import es.upm.miw.resources.AllMiwResourcesFunctionalTests;
import es.upm.miw.services.AllMiwServicesIntegrationTests;
import es.upm.miw.services.PdfBuilderTest;
import es.upm.miw.utils.AllMiwUtilsTests;

@RunWith(Suite.class)
@SuiteClasses({
    AllMiwDocumentsCoreTests.class,
    AllMiwUtilsTests.class,
    PdfBuilderTest.class,
    
    AllMiwRepositoriesCoreIntegrationTets.class,
    AllMiwServicesIntegrationTests.class,
    
    AllMiwResourcesFunctionalTests.class,
})
public class AllMiwTests {
}
