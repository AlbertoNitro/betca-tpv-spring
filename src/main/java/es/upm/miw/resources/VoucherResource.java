package es.upm.miw.resources;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.VoucherController;
import es.upm.miw.dtos.VoucherDto;
import es.upm.miw.resources.exceptions.VoucherConsumedException;
import es.upm.miw.resources.exceptions.VoucherReferenceNotFoundException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(VoucherResource.VOUCHERS)
public class VoucherResource {
	
	public static final String VOUCHERS = "/vouchers";

    public static final String REFERENCE = "/{reference}";
    
    @Autowired
    private VoucherController voucherController;
        
    @PostMapping
    public void createVoucher(@Valid @RequestBody VoucherDto voucherDto) {
    	
    	if ( voucherDto.getValue() == null) {
            voucherDto.setValue( new BigDecimal(11) );
        }
    	
        this.voucherController.createVoucher( voucherDto.getValue() );
    }
    
    @RequestMapping(value = REFERENCE, method = RequestMethod.GET)
    public VoucherDto readVoucher(@PathVariable String reference) throws VoucherReferenceNotFoundException {
    	
    	return this.voucherController.readVoucher( reference ).orElseThrow(() -> new VoucherReferenceNotFoundException());
    }
    
    @PatchMapping(value = REFERENCE)
    public void consumeVoucher(@PathVariable String reference) throws VoucherReferenceNotFoundException, VoucherConsumedException {
        
    	if ( !this.voucherController.existsVoucher( reference ) ) {
    		throw new VoucherReferenceNotFoundException();
    	}
    	
    	if ( this.voucherController.consumedVoucher( reference ) ) {
    		throw new VoucherConsumedException();
    	}
    	
    	this.voucherController.consumeVoucher( reference );
    }
    
    @GetMapping
    public List<VoucherDto> readVoucherAll() {
    	
    	return this.voucherController.readVoucherAll();

    }

}
