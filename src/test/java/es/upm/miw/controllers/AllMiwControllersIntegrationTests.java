package es.upm.miw.controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ArticleControllerIT.class,
    BudgetControllerIT.class,
    CashierClosureControllerIT.class,
    ProviderControllerIT.class,
    TicketControllerIT.class,
    UserControllerIT.class,
    VoucherControllerIT.class
})
public class AllMiwControllersIntegrationTests {

}
