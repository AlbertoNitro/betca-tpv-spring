package es.upm.miw.repositories.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ArticleFamilyRepositoriesIT.class,
    ArticleRepositoryIT.class,
    CashierClosureRepositoryIT.class,
    InvoiceRepositoryIT.class,
    ProviderRepositoryIT.class,
    TicketRepositoryIT.class,
    UserRepositoryIT.class,
    VoucherRepositoryIT.class,
    OrderRepositoryIT.class,
    OrderBodyRepositoryIT.class
    })
public class AllMiwRepositoriesCoreIntegrationTests {

}
