package es.upm.miw.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.ProviderDto;
import es.upm.miw.resources.exceptions.FieldAlreadyExistException;
import es.upm.miw.resources.exceptions.NotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class ProviderControllerIT {

    @Autowired
    private ProviderController providerController;
    
   
    @Test(expected = NotFoundException.class)
    public void testPutProviderNotFoundException() throws NotFoundException, FieldAlreadyExistException {
        ProviderDto providerDto= new ProviderDto();
        providerDto.setCompany("");
        this.providerController.putProvider("0", providerDto);
    }

        
}
