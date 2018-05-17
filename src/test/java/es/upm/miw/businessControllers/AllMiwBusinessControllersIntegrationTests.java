package es.upm.miw.businessControllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        ArticleControllerIT.class,
        BudgetControllerIT.class,
        CashierClosureControllerIT.class,
        InvoiceControllerIT.class,
        ProviderControllerIT.class,
        TicketControllerIT.class,
        UserControllerIT.class,
        VoucherControllerIT.class,
        OrderControllerIT.class
})
public class AllMiwBusinessControllersIntegrationTests {

}
