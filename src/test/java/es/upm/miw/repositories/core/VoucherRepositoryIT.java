package es.upm.miw.repositories.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

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
    public void testVoucherNotUsed() {
        assertFalse(voucherRepository.findByValue(new BigDecimal("666.666")).get(0).isUsed());
    }

    @Test
    public void testVoucherUsed() {
        assertTrue(voucherRepository.findByValue(new BigDecimal("0.666")).get(0).isUsed());
    }

}
