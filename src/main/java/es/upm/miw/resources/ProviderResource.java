package es.upm.miw.resources;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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

import es.upm.miw.dtos.ProviderDto;
import es.upm.miw.dtos.ProviderMinimumDto;
import es.upm.miw.resources.exceptions.ForbiddenException;
import es.upm.miw.resources.exceptions.UserFieldAlreadyExistException;
import es.upm.miw.resources.exceptions.UserIdNotFoundException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(ProviderResource.PROVIDERS)
public class ProviderResource {

    public static final String PROVIDERS = "/providers";

    public static final String ID = "/{id}";
    
    @PostMapping
    public void createProvider(@Valid @RequestBody ProviderDto providerDto) throws UserFieldAlreadyExistException {
        //TODO
    }

    @PutMapping(value = ID)
    public void putProvider(@PathVariable String id, @Valid @RequestBody ProviderDto providerDto)
            throws ForbiddenException, UserIdNotFoundException, UserFieldAlreadyExistException {
        //TODO
    }
    
    @DeleteMapping(value = ID)
    public void deleteCustomer(@PathVariable String mobile) throws ForbiddenException {
        //TODO
    }

    
    @RequestMapping(value = ID, method = RequestMethod.GET)
    public ProviderDto readProvider(@PathVariable String id) throws UserIdNotFoundException {
        ProviderDto provider1 = new ProviderDto("p1","Provider1","","","","",true);
        return provider1;
    }

    @GetMapping
    public List<ProviderMinimumDto> readProviderAll() {
        
        ProviderMinimumDto provider1 = new ProviderMinimumDto("p1","Provider1");
        ProviderMinimumDto provider2 = new ProviderMinimumDto("p2","Provider2");
        ProviderMinimumDto provider3 = new ProviderMinimumDto("p3","Provider3");
        
        List<ProviderMinimumDto> providersList = new ArrayList<ProviderMinimumDto>();
        providersList.add(provider1);
        providersList.add(provider2);
        providersList.add(provider3);
        
        return providersList;
    }

}
