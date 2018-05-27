package es.upm.miw.restControllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.businessControllers.ProviderController;
import es.upm.miw.dtos.ProviderDto;
import es.upm.miw.dtos.ProviderMinimumDto;
import es.upm.miw.exceptions.FieldAlreadyExistException;
import es.upm.miw.exceptions.NotFoundException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(ProviderResource.PROVIDERS)
public class ProviderResource {

    public static final String PROVIDERS = "/providers";

    public static final String ID_ID = "/{id}";

    public static final String ACTIVES = "/actives";

    @Autowired
    private ProviderController providerController;

    @PostMapping
    public void createProvider(@Valid @RequestBody ProviderDto providerDto) throws MethodArgumentNotValidException, DuplicateKeyException {
        this.providerController.createProvider(providerDto);
    }

    @GetMapping(value = ID_ID)
    public ProviderDto readProvider(@PathVariable String id) throws NotFoundException {
        return this.providerController.readProvider(id);
    }

    @PutMapping(value = ID_ID)
    public void putProvider(@PathVariable String id, @Valid @RequestBody ProviderDto providerDto)
            throws NotFoundException, FieldAlreadyExistException {
        this.providerController.putProvider(id, providerDto);
    }

    @GetMapping
    public List<ProviderMinimumDto> readAll() {
        return this.providerController.readAll();
    }

    @GetMapping(value = ACTIVES)
    public List<ProviderMinimumDto> readAllActives() {
        return this.providerController.readAllActives();
    }

}
