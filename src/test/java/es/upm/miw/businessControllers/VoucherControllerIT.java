package es.upm.miw.businessControllers;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.businessControllers.VoucherController;
import es.upm.miw.documents.core.Voucher;
import es.upm.miw.exceptions.NotFoundException;
import es.upm.miw.exceptions.VoucherException;
import es.upm.miw.repositories.core.VoucherRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class VoucherControllerIT {

    @Autowired
    private VoucherController voucherController;

    private Voucher voucher;

    @Autowired
    private VoucherRepository voucherRepository;

    @Before
    public void before() {
        this.voucher = new Voucher(new BigDecimal(32));
        this.voucherRepository.save(this.voucher);
    }
    
    @Test
    public void testConsumedVoucherTrue() throws NotFoundException, VoucherException {
        this.voucherController.consumeVoucher(this.voucher.getId());
    }

    @After
    public void delete() {
        this.voucherRepository.delete(this.voucher);
    }

}
