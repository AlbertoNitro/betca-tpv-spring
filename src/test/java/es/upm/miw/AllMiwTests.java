package es.upm.miw;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.upm.miw.documents.users.AllMiwDocumentsUsersTests;
import es.upm.miw.repositories.users.AllMiwRepositoriesUsersIntegrationTests;
import es.upm.miw.resources.AllMiwResourcesFunctionalTests;

@RunWith(Suite.class)
@SuiteClasses({
    ApplicationTest.class,
    ApplicationIT.class,
    ApplicationFunctionalTesting.class,
    AllMiwDocumentsUsersTests.class,
    AllMiwRepositoriesUsersIntegrationTests.class,
    AllMiwResourcesFunctionalTests.class
})
public class AllMiwTests {
}
