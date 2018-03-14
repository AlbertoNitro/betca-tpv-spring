package es.upm.miw.resources;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.dtos.VoucherDto;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(VoucherResource.VOUCHERS)
public class VoucherResource {
	
	public static final String VOUCHERS = "/vouchers";

    public static final String REFERENCE = "/{reference}";
    
    private VoucherDto voucher1;
    
    @PostMapping
    public void createVoucher(@Valid @RequestBody VoucherDto voucherDto) throws Exception {
        //TODO
    	// Empty for the mock
    }
    
    @RequestMapping(value = REFERENCE, method = RequestMethod.GET)
    public VoucherDto readVoucher(@PathVariable String reference) throws Exception {
    	
    	VoucherDto voucherDto;
    	
    	if ( reference == "1" ) {
    		voucherDto = this.voucher1;
    	}else {
    		throw new Exception();
    	}
    	
        return voucherDto;
    }
    
    @PatchMapping(value = REFERENCE)
    public void consumeVoucher(@PathVariable String reference) throws Exception {
        //TODO
    	if ( reference == "1" ) {
   		 	this.voucher1.setUsed( true );
	   	}else {
	   		throw new Exception();
	   	}
    }
    
    @GetMapping
    public List<VoucherDto> readVoucherAll() {
    	
    	List<VoucherDto> vouchersList = new ArrayList<VoucherDto>();
        
        this.voucher1 = new VoucherDto( "1", new BigDecimal(11) );
        VoucherDto voucher2 = new VoucherDto( "2", new BigDecimal(22) );
        VoucherDto voucher3 = new VoucherDto( "3", new BigDecimal(33) );
        
        vouchersList.add(this.voucher1);
        vouchersList.add(voucher2);
        vouchersList.add(voucher3);
        
        return vouchersList;
    }

}
