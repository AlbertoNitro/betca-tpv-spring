package es.upm.miw.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Voucher;
import es.upm.miw.dtos.VoucherDto;
import es.upm.miw.repositories.core.VoucherRepository;

@Controller
public class VoucherController {
	
	@Autowired
    private VoucherRepository voucherRepository;
	
	public void createVoucher( BigDecimal value ) {
		Voucher voucher = new Voucher( value );
		this.voucherRepository.save( voucher );
	}
	
	public Optional<VoucherDto> readVoucher(String reference) {
		
		Voucher voucherBd = this.voucherRepository.findByReference( reference );
		
		if ( voucherBd == null ) {
			return Optional.empty();
		}else {
			return Optional.of( new VoucherDto( voucherBd ) );
		}
    }
	
	public List<VoucherDto> readVoucherAll() {
		
		List <Voucher> voucherList = this.voucherRepository.findAll();
		List<VoucherDto> voucherDtoList = new ArrayList<VoucherDto>();
		
		for ( int i = 0; i < voucherList.size(); i++ ) {
			voucherDtoList.add( new VoucherDto( voucherList.get( i ) ) );
		}
		
		return voucherDtoList;
    }
	
	public BigDecimal consumeVoucher( String reference ) {
		Voucher voucher = this.voucherRepository.findByReference( reference );
		assert voucher != null;
		voucher.setDateOfUse( new Date() );
		this.voucherRepository.save( voucher );
		return voucher.getValue();
	}
	
	public boolean deleteVoucher(String reference) {
        Voucher voucherBd = this.voucherRepository.findByReference(reference);
        if (voucherBd != null) {
            this.voucherRepository.delete( voucherBd );
            return true;
        } else {
            return false;
        }
    }
	
	public boolean existsVoucher( String reference ) {
		return this.voucherRepository.findByReference(reference) != null;
	}
	
	public boolean consumedVoucher( String reference ) {
		Voucher voucher = this.voucherRepository.findByReference(reference);
		return voucher.isUsed();
	}

}
