package es.upm.miw.controllers;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class VoucherControllerIT {
	
	@Autowired
    private VoucherController voucherController;
	
	
	
	@Test
    public void testExistsVoucherFalse() {

    }
	
	@Test
    public void testExistsVoucherTrue() {

    }
	
	@Test
    public void consumedVoucherFalse() {

    }
	
	@Test
    public void consumedVoucherTrue() {

    }

}
