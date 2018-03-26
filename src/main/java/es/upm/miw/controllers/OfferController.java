package es.upm.miw.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Offer;
import es.upm.miw.dtos.OfferInputDto;
import es.upm.miw.dtos.OfferOutputDto;
import es.upm.miw.repositories.core.OfferRepository;

@Controller
public class OfferController {
    @Autowired
    private OfferRepository offerRepository;

    public void createOffer(OfferInputDto offerInputDto) {
    }
    
    public Optional<OfferOutputDto> readUser(String code) {
    	return Optional.empty();
    }
    
    public boolean putOffer(String cocde, OfferInputDto offerInputDto) {
        return true;
    }
    
    public boolean deleteOffer(String code) {
    	return true;
    }

    public List<OfferOutputDto> readCustomerAll() {
        return new ArrayList<OfferOutputDto>();
    }
    
    public boolean codeRepeated(OfferInputDto offerInputDto) {
    	return this.codeRepeated(offerInputDto.getCode(), offerInputDto);
    }

    public boolean codeRepeated(String code, OfferInputDto offerInputDto) {
    	Offer offer = offerRepository.findByCode(offerInputDto.getCode());
    	if (offer == null) return false;
    	if (code == null) return false;
    	return offer.getCode().equals(code);
    }
    
    public boolean isExpiration(OfferInputDto offerInputDto) {
    	return this.isExpiration(offerInputDto.getExpiration(), offerInputDto);
    }
    
    public boolean isExpiration(Date expiration, OfferInputDto offerInputDto) {
    	Offer offer = offerRepository.findByCode(offerInputDto.getCode());
    	return offer != null && expiration != null && offer.getExpiration().before(new Date());
    }
}
