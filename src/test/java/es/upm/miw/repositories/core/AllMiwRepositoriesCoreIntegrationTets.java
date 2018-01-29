package es.upm.miw.repositories.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    VoucherRepositoryIT.class,
    ProviderRepositoryIT.class
    })
public class AllMiwRepositoriesCoreIntegrationTets {

}
