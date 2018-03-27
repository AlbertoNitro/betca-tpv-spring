package es.upm.miw.resources;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    AdminResourceFunctionalTesting.class,
    ArticleResourceFunctionalTesting.class,
    ArticleFamilyResourceFunctionalTesting.class,
    BudgetResourceFunctionalTesting.class,
    CashierClosureResourceFunctionalTesting.class,
    InvoiceResourceFuntionalTesting.class,
    ProviderResourceFunctionalTesting.class,
    TicketResourceFunctionalTesting.class,
    TokenResourceFunctionalTesting.class,
    UserResourceFunctionalTesting.class,
    VoucherResourceFunctionalTesting.class,
    OrderResourceFunctionalTesting.class,
    OrderBodyResourceFunctionalTesting.class
})
public class AllMiwResourcesFunctionalTests {

}
