package es.upm.miw;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.upm.miw.documents.users.AllMiwDocumentsUsersTests;
import es.upm.miw.repositories.users.AllMiwRepositoriesUsersIntegrationTests;
import es.upm.miw.resources.AllMiwResourcesFunctionalTests;
import es.upm.miw.services.DatabaseSeederServiceIT;

@RunWith(Suite.class)
@SuiteClasses({
    ApplicationTest.class,
    AllMiwDocumentsUsersTests.class,
    
    AllMiwRepositoriesUsersIntegrationTests.class,
    ApplicationIT.class,
    
    AllMiwResourcesFunctionalTests.class,
    ApplicationFunctionalTesting.class,
    DatabaseSeederServiceIT.class
})
public class AllMiwTests {
}
