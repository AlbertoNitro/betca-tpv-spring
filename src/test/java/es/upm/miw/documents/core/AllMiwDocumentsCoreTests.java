package es.upm.miw.documents.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ArticleFamilyTest.class,
    BudgetTest.class,
    EncryptTest.class,
    ShoppingTest.class,
    TicketTest.class
})
public class AllMiwDocumentsCoreTests {

}
