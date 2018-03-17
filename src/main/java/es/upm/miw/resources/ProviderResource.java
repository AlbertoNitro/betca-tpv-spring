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

import es.upm.miw.controllers.ProviderController;
import es.upm.miw.dtos.ProviderDto;
import es.upm.miw.dtos.ProviderMinimumDto;
import es.upm.miw.resources.exceptions.ForbiddenException;
import es.upm.miw.resources.exceptions.ProviderFieldAlreadyExistException;
import es.upm.miw.resources.exceptions.ProviderIdNotFoundException;
import es.upm.miw.resources.exceptions.UserIdNotFoundException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(ProviderResource.PROVIDERS)
public class ProviderResource {

    public static final String PROVIDERS = "/providers";

    public static final String ID = "/{id}";
    
    @Autowired
    private ProviderController providerController;

    @PostMapping
    public void createProvider(@Valid @RequestBody ProviderDto providerDto) throws ProviderFieldAlreadyExistException {
        if (this.providerController.companyRepeated(providerDto)) {
            throw new ProviderFieldAlreadyExistException("Existing Company");
        }
        this.providerController.createProvider(providerDto);
    }

    @PutMapping(value = ID)
    public void putProvider(@PathVariable String id, @Valid @RequestBody ProviderDto providerDto)
            throws ForbiddenException, ProviderFieldAlreadyExistException, ProviderIdNotFoundException {
        if (!this.providerController.existsId(id)) {
            throw new ProviderIdNotFoundException("Not existing Provider");
        }
        if (this.providerController.companyRepeated(providerDto)) {
            throw new ProviderFieldAlreadyExistException("Existing Company");
        }
        if (!this.providerController.putProvider(id, providerDto)) {
            throw new ForbiddenException();
        }
    }
    
    @DeleteMapping(value = ID)
    public void deleteCustomer(@PathVariable String id) throws ForbiddenException {
        if (!this.providerController.deleteProvider(id)) {
            throw new ForbiddenException();
        }
    }

    @RequestMapping(value = ID, method = RequestMethod.GET)
    public ProviderDto readProvider(@PathVariable String id) throws UserIdNotFoundException {
        return this.providerController.readProvider(id).orElseThrow(() -> new UserIdNotFoundException(id));
    }

    @GetMapping
    public List<ProviderMinimumDto> readProviderAll() {
        return this.providerController.readProviderAll();
    }

}
