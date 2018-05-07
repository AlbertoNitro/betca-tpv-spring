package es.upm.miw.repositories.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Provider;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class ProviderRepositoryIT {

    @Autowired
    private ProviderRepository providerRepository;

    @Test
    public void testFindOne() {
        assertEquals("company-p1", this.providerRepository.findOne("provider1").getCompany());
    }

    @Test(expected = DuplicateKeyException.class)
    public void testRepeatcompany() {
        this.providerRepository.save(Provider.builder().company("company-p1").build());
    }

}
