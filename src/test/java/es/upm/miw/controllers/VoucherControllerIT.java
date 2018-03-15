package es.upm.miw.controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Voucher;
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
        this.voucher = new Voucher( new BigDecimal( 32 ) );
        this.voucherRepository.save( this.voucher );
    }
	
	@After
    public void delete() {
		this.voucherRepository.delete( this.voucher );
    }
	
	@Test
    public void testExistsVoucherFalse() {
		
		assertFalse( this.voucherController.existsVoucher( "0" ) );		
		
    }
	
	@Test
    public void testExistsVoucherTrue() {

		assertTrue( this.voucherController.existsVoucher( this.voucher.getReference() ) );		
		
    }
	
	@Test
    public void consumedVoucherFalse() {

		assertFalse( this.voucherController.consumedVoucher( this.voucher.getReference() ) );
		
    }
	
	@Test
    public void consumedVoucherTrue() {
		
		assertFalse( this.voucherController.consumedVoucher( this.voucher.getReference() ) );
		this.voucher.setDateOfUse( new Date() );
		this.voucherRepository.save( this.voucher );
		assertTrue( this.voucherController.consumedVoucher( this.voucher.getReference() ) );
		
    }

}
