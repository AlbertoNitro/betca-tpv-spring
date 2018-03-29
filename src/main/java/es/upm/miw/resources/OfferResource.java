package es.upm.miw.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.OfferController;
import es.upm.miw.dtos.OfferInputDto;
import es.upm.miw.dtos.OfferOutputDto;
import es.upm.miw.resources.exceptions.ForbiddenException;
import es.upm.miw.resources.exceptions.OfferCodeNotFoundException;
import es.upm.miw.resources.exceptions.OfferCodeRepeatedException;
import es.upm.miw.resources.exceptions.OfferInvalidExpirationDateException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(OfferResource.OFFERS)
public class OfferResource {
	
    public static final String OFFERS = "/offers";
    public static final String OFFER_ID = "/{code}";
    
    @Autowired
    private OfferController offerController;
    
    @PostMapping
    public void createOffer(@Valid @RequestBody OfferInputDto offerInputDto) 
    		throws OfferCodeRepeatedException, OfferInvalidExpirationDateException {    	
    	if (!offerController.codeRepeated(offerInputDto)) {
    		throw new OfferInvalidExpirationDateException();
    	}
    	
    	if (!offerController.isExpirationDateValid(offerInputDto)) {
    		throw new OfferInvalidExpirationDateException();
    	}
    	offerController.createOffer(offerInputDto);
    }

    @PutMapping(value = OFFER_ID)
    public void putOffer(@Valid @RequestBody OfferInputDto offerInputDto) 
    		throws OfferInvalidExpirationDateException, ForbiddenException, OfferInvalidExpirationDateException{
    	if (!offerController.isExpirationDateValid(offerInputDto)) {
    		throw new OfferInvalidExpirationDateException();
    	}
    	if (!offerController.codeRepeated(offerInputDto)) {
    		throw new OfferInvalidExpirationDateException();
    	}
    	if (!offerController.putOffer(offerInputDto)) {
    		throw new ForbiddenException(); 
    	}
    }

    @DeleteMapping(value = OFFER_ID)
    public void deleteOffer(@PathVariable String code) throws ForbiddenException {	
    	if (!offerController.deleteOffer(code)) {
    		throw new ForbiddenException(); 
    	}
    }

    @RequestMapping(value = OFFER_ID, method = RequestMethod.GET)
    public OfferOutputDto readOffer(@PathVariable String code) throws OfferCodeNotFoundException {
    	 return this.offerController.readOffer(code).orElseThrow(() -> new OfferCodeNotFoundException(code));
    }

    @GetMapping
    public List<OfferOutputDto> readOfferAll() {
        return this.offerController.readOfferAll();
    }
}
