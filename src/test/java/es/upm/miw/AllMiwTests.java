package es.upm.miw;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.upm.miw.documents.core.AllMiwDocumentsCoreTests;
import es.upm.miw.repositories.core.AllMiwRepositoriesCoreIntegrationTets;
import es.upm.miw.resources.AllMiwResourcesFunctionalTests;
import es.upm.miw.services.DatabaseSeederServiceIT;

@RunWith(Suite.class)
@SuiteClasses({
    ApplicationTest.class,
    AllMiwDocumentsCoreTests.class,
    
    AllMiwRepositoriesCoreIntegrationTets.class,
    ApplicationIT.class,
    
    AllMiwResourcesFunctionalTests.class,
    ApplicationFunctionalTesting.class,
    DatabaseSeederServiceIT.class
})
public class AllMiwTests {
}
