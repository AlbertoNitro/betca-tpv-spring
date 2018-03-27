package es.upm.miw.controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ArticleControllerIT.class,
    ArticleFamilyControllerIT.class,
    BudgetControllerIT.class,
    CashierClosureControllerIT.class,
    InvoiceControllerIT.class,
    ProviderControllerIT.class,
    TicketControllerIT.class,
    UserControllerIT.class,
    VoucherControllerIT.class,
    OrderControllerIT.class,
    OrderBodyControllerIT.class
})
public class AllMiwControllersIntegrationTests {

}
