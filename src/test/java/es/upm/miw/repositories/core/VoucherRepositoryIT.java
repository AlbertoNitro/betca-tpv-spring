package es.upm.miw.repositories.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Voucher;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class VoucherRepositoryIT {

    @Autowired
    private VoucherRepository voucherRepository;

    @Test
    public void testVoucherNotUsed() {
        assertFalse(this.voucherRepository.findByValue(new BigDecimal("666.666")).get(0).isUsed());
    }

    @Test
    public void testVoucherUsed() {
        assertTrue(this.voucherRepository.findByValue(new BigDecimal("0.666")).get(0).isUsed());
    }

    @Test
    public void testFindByDateOfUseGreaterThan() throws ParseException {
        Date date = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse("2018-01-06 19:00:22");
        List<Voucher> voucherList = this.voucherRepository.findByDateOfUseGreaterThan(date);
        assertEquals(50.6, voucherList.get(0).getValue().doubleValue(),10-10);
    }

}
