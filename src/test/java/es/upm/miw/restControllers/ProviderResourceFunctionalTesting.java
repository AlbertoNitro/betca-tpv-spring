package es.upm.miw.restControllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dataServices.DatabaseSeederService;
import es.upm.miw.dtos.ProviderDto;
import es.upm.miw.restControllers.ProviderResource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class ProviderResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private RestService restService;

    @Autowired
    private DatabaseSeederService databaseSeederService;

    private ProviderDto providerDto;

    @Before
    public void before() {
        this.restService.loginAdmin();
        this.providerDto = new ProviderDto();
        this.providerDto.setCompany("TCompany");
    }

    @Test
    public void testCreateProvider() {
        this.restService.loginAdmin().restBuilder().path(ProviderResource.PROVIDERS).body(providerDto).post().build();
        this.databaseSeederService.resetDB();
    }

    @Test
    public void testCreateProviderCompanyNullException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        providerDto.setCompany(null);
        restService.loginAdmin().restBuilder().path(ProviderResource.PROVIDERS).body(this.providerDto).post().build();
    }

    @Test
    public void testPutProvider() {
        providerDto.setId("provider1");
        providerDto.setCompany("company-p1");
        providerDto.setAddress("TAddress");
        restService.loginAdmin().restBuilder().path(ProviderResource.PROVIDERS).path(ProviderResource.ID_ID).expand("provider1")
                .body(providerDto).put().build();
        providerDto = restService.restBuilder(new RestBuilder<ProviderDto>()).clazz(ProviderDto.class).path(ProviderResource.PROVIDERS)
                .path(ProviderResource.ID_ID).expand("provider1").get().build();
        assertEquals("TAddress", providerDto.getAddress());
        this.databaseSeederService.resetDB();
    }

    @Test
    public void testCreateProviderCompanyFieldAlreadyExistException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        providerDto.setCompany("company-p1");
        restService.loginAdmin().restBuilder().path(ProviderResource.PROVIDERS).body(providerDto).post().build();
    }

    @Test
    public void testReadProviderAll() {
        restService.restBuilder(new RestBuilder<String>()).clazz(String.class).path(ProviderResource.PROVIDERS).get().build();
    }

}
