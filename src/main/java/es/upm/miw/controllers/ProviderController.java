package es.upm.miw.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Provider;
import es.upm.miw.dtos.ProviderDto;
import es.upm.miw.dtos.ProviderMinimumDto;
import es.upm.miw.repositories.core.ProviderRepository;
import es.upm.miw.resources.exceptions.FieldAlreadyExistException;
import es.upm.miw.resources.exceptions.NotFoundException;

@Controller
public class ProviderController {

    @Autowired
    private ProviderRepository providerRepository;

    public void createProvider(ProviderDto provierDto) {
        Provider provider = Provider.builder().company(provierDto.getCompany()).nif(provierDto.getNif()).address(provierDto.getAddress())
                .phone(provierDto.getPhone()).email(provierDto.getEmail()).note(provierDto.getNote()).build();
        this.providerRepository.save(provider);
    }

    public ProviderDto readProvider(String id) throws NotFoundException {
        return new ProviderDto(readOne(id));
    }

    private Provider readOne(String id) throws NotFoundException {
        Provider provider = this.providerRepository.findOne(id);
        if (provider == null) {
            throw new NotFoundException("Provider id (" + id + ")");
        }
        return provider;
    }

    private void assertCompanyUnique(String id, ProviderDto providerDto) throws FieldAlreadyExistException {
        Provider provider = this.providerRepository.findByCompany(providerDto.getCompany());
        if (null != provider && !provider.getId().equals(id)) {
            throw new FieldAlreadyExistException("Provider company (" + providerDto.getCompany() + ")");
        }
    }

    public void putProvider(String id, ProviderDto providerDto) throws NotFoundException, FieldAlreadyExistException {
        Provider provider = readOne(id);
        this.assertCompanyUnique(id, providerDto);
        provider.setCompany(providerDto.getCompany());
        provider.setNif(providerDto.getNif());
        provider.setAddress(providerDto.getAddress());
        provider.setPhone(providerDto.getPhone());
        provider.setEmail(providerDto.getEmail());
        provider.setNote(providerDto.getNote());
        provider.setActive(providerDto.getActive());
        this.providerRepository.save(provider);
    }

    public List<ProviderMinimumDto> readAll() {
        return this.providerRepository.findMinimumAll();
    }

    public List<ProviderMinimumDto> readAllActives() {
        return this.providerRepository.findMinimumAllActives();
    }

}
