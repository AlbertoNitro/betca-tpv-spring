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
    	Offer offer = new Offer(offerInputDto.getCode(),
    							offerInputDto.getPercentage(), 
    							offerInputDto.getExpiration(),
    							offerInputDto.getDescription());
    	offerRepository.save(offer);
    }
    
    public Optional<OfferOutputDto> readUser(String code) {
        Offer offerInDb = this.offerRepository.findByCode(code);
        if (offerInDb == null) {
            return Optional.empty();
        } else {
            return Optional.of(new OfferOutputDto(offerInDb));
        }
    }
    
    public boolean putOffer(String code, OfferInputDto offerInputDto) {
    	 Offer offerInDb = this.offerRepository.findByCode(code);
         if (offerInDb == null) {
             return false;
         } else {
             offerInDb.setPercentage(offerInputDto.getPercentage());
             offerInDb.setExpiration(offerInputDto.getExpiration());
             offerInDb.setDescription(offerInputDto.getDescription());
             return true;
         }
    }
    
    public boolean deleteOffer(String code) {
        Offer offerInDb = this.offerRepository.findByCode(code);
        if (offerInDb == null) {
            return true;
        } else {
            this.offerRepository.delete(offerInDb);
            return true;
        } 
    }

    public List<OfferOutputDto> readOfferAll() {
        List<Offer> offerList = this.offerRepository.findAll();
        List<OfferOutputDto> offerOutputDtoList = new ArrayList<OfferOutputDto>();
        for (int i = 0; i < offerList.size(); i++) {
            offerOutputDtoList.add(new OfferOutputDto(offerList.get(i)));
        }
        return offerOutputDtoList;
    }
    
    public boolean codeRepeated(OfferInputDto offerInputDto) {
    	return this.codeRepeated(offerInputDto.getCode(), offerInputDto);
    }

    public boolean codeRepeated(String code, OfferInputDto offerInputDto) {
    	Offer offer = offerRepository.findByCode(offerInputDto.getCode());
    	return offer != null && code != null && !offer.getCode().equals(code);
    }
    
    public boolean isExpirationDateValid(OfferInputDto offerInputDto) {
    	return this.isExpirationDateValid(offerInputDto.getExpiration(), offerInputDto);
    }
    
    public boolean isExpirationDateValid(Date expiration, OfferInputDto offerInputDto) {
    	Offer offer = offerRepository.findByCode(offerInputDto.getCode());
    	return offer != null && expiration != null && offer.getExpiration().after(new Date());
    }
}
