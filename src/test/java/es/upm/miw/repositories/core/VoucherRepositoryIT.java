package es.upm.miw.repositories.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class VoucherRepositoryIT {

    @Autowired
    private VoucherRepository voucherRepository;

    @Test
    public void testFindVoucher() {
        assertEquals(28.34,voucherRepository.findOne("eAgq2L-qZj7QlDKhxVetXs9yzLo").getValue().doubleValue(),10e-5);
    }
    
    @Test
    public void testVoucherUsed() {
        assertTrue(voucherRepository.findOne("XWGtHHGHG2Go17Pi4L36pPWmfiE").isUsed());
    }

}
