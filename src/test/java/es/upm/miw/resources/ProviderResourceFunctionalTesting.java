package es.upm.miw.resources;

import static org.junit.Assert.assertEquals;

import org.junit.After;
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

import es.upm.miw.dtos.ProviderDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class ProviderResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Autowired
    private RestService restService;
    
    private ProviderDto providerDto;
    
    @Before
    public void before() {
        this.restService.loginAdmin();
        this.providerDto = new ProviderDto("TP","TCompany",null,null,null,null,true);
    }
    
    @Test
    public void testCreateProvider() {
        restService.restBuilder().path(ProviderResource.PROVIDERS).body(providerDto).post().build();
    }
    
    @Test
    public void testCreateProviderCompanyNullException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        providerDto.setCompany(null);
        restService.restBuilder().path(ProviderResource.PROVIDERS).body(this.providerDto).post().build();
    }
    
    @Test
    public void testPutProvider() {
        restService.restBuilder().path(ProviderResource.PROVIDERS).body(providerDto).post().build();
        providerDto.setAddress("TAddress");
        restService.restBuilder().path(ProviderResource.PROVIDERS).path(ProviderResource.ID).expand("TP").body(providerDto).put().build();
        assertEquals("TAddress", providerDto.getAddress());
    }
    
    @Test
    public void testCreateProviderCompanyFieldAlreadyExistException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        providerDto.setCompany("company-p1");
        restService.restBuilder().path(ProviderResource.PROVIDERS).body(providerDto).post().build();
    }

    @Test
    public void testReadProvider() {
        String json=restService.restBuilder(new RestBuilder<String>()).clazz(String.class).path(ProviderResource.PROVIDERS).path(ProviderResource.ID).expand("provider1").get().build();
        System.out.println("------------>"+json);
    }
    
    @Test
    public void testReadProviderAll() {
        String json=restService.restBuilder(new RestBuilder<String>()).clazz(String.class).path(ProviderResource.PROVIDERS).get().build();
        System.out.println("------------>"+json);
    }
    
    @After
    public void delete() {
        restService.restBuilder().path(ProviderResource.PROVIDERS).path(ProviderResource.ID).expand("TP").delete().build();
    }

}
