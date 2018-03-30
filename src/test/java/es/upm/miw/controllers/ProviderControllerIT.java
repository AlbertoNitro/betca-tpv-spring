package es.upm.miw.controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.ProviderDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class ProviderControllerIT {

    @Autowired
    private ProviderController providerController;

    private ProviderDto providerDto;
    
    @Before
    public void before() {
        this.providerDto = new ProviderDto();
    }
    
    @Test
    public void testCompanyRepeatedTrue() {
        this.providerDto.setCompany("company-p1");
        assertTrue(providerController.companyRepeated(this.providerDto));
    }
    
    @Test
    public void testCompanyRepeatedFalse() {
        this.providerDto.setCompany("non exist");
        assertFalse(providerController.companyRepeated(this.providerDto));
    }
        
}
