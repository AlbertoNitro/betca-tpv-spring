package es.upm.miw.resources;

import java.util.ArrayList;
import java.util.List;

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

import es.upm.miw.documents.core.Offer;
import es.upm.miw.dtos.OfferInputDto;
import es.upm.miw.dtos.OfferOutputDto;
import es.upm.miw.repositories.core.OfferRepository;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(OfferResource.OFFERS)
public class OfferResource {

    public static final String OFFERS = "/offers";

    public static final String OFFER_ID = "/{id}";
    
    @Autowired
    private OfferRepository offerRepository;
    
    @PostMapping
    public void createOffer(@RequestBody OfferInputDto offerDto)  {
        Offer offer = new Offer(offerDto.getCode(), offerDto.getPercentage(), offerDto.getExpiration());
        this.offerRepository.save(offer);
    }

    @PutMapping(value = OFFER_ID)
    public void putOffer() {
    	System.out.println("putting offer");
    }

    @DeleteMapping(value = OFFER_ID)
    public void deleteOffer(@PathVariable String id) {	
    	System.out.println("Deleting Offer with id: " + id );
    }

    @RequestMapping(value = OFFER_ID, method = RequestMethod.GET)
    public void readOffer(@PathVariable String id){
    	System.out.println("Deleting Offer with id: " + id );
    }

    @GetMapping
    public List<OfferOutputDto> readOfferAll() {
        List<Offer> offerList = this.offerRepository.findAll();
        List<OfferOutputDto> offerOutputDtoList = new ArrayList<OfferOutputDto>();
        for (int i = 0; i < offerList.size(); i++) {
            offerOutputDtoList.add(new OfferOutputDto(offerList.get(i)));
        }
        return offerOutputDtoList;
    }
}