package es.upm.miw.resources;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    AdminResourceFunctionalTesting.class,
    ArticleResourceFunctionalTesting.class,
    BudgetResourceFunctionalTesting.class,
    CashierClosureResourceFunctionalTesting.class,
    TicketResourceFunctionalTesting.class,
    TokenResourceFunctionalTesting.class,
    UserResourceFunctionalTesting.class
})
public class AllMiwResourcesFunctionalTests {

}
