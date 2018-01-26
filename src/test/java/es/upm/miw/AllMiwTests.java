package es.upm.miw;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ApplicationTest.class,
    ApplicationIT.class,
    ApplicationFunctionalTesting.class
})
public class AllMiwTests {
}
