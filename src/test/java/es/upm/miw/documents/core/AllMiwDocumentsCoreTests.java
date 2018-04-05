package es.upm.miw.documents.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    BudgetTest.class,
    EncryptTest.class,
    ShoppingTest.class,
    TicketTest.class,
    UserTest.class
})
public class AllMiwDocumentsCoreTests {

}
