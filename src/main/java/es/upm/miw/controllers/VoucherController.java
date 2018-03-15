package es.upm.miw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Voucher;
import es.upm.miw.repositories.core.VoucherRepository;

@Controller
public class VoucherController {
	
	@Autowired
    private VoucherRepository voucherRepository;
	
	public boolean existsVoucher( String reference ) {
		return this.voucherRepository.findByReference(reference) != null;
	}
	
	public boolean consumedVoucher( String reference ) {
		Voucher voucher = this.voucherRepository.findByReference(reference);
		return voucher.isUsed();
	}

}
