package es.upm.miw.dataServices;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.upm.miw.businessServices.PdfServiceIT;

@RunWith(Suite.class)
@SuiteClasses({
    DatabaseSeederServiceIT.class,
    PdfServiceIT.class
})
public class AllMiwDataServicesIntegrationTests {

}
