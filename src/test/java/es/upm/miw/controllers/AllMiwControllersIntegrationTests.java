package es.upm.miw.controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ArticleControllerIT.class,
    CashierClosureControllerIT.class,
    TicketControllerIT.class,
    UserControllerIT.class
})
public class AllMiwControllersIntegrationTests {

}
