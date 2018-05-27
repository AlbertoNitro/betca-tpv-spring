package es.upm.miw.businessControllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    BarcodeTest.class,
    EncryptingTest.class
})
public class AllMiwUtilsTests {

}
