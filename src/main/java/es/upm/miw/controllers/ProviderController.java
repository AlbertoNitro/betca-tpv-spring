package es.upm.miw.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Provider;
import es.upm.miw.dtos.ProviderDto;
import es.upm.miw.dtos.ProviderMinimumDto;
import es.upm.miw.repositories.core.ProviderRepository;

@Controller
public class ProviderController {

    @Autowired
    private ProviderRepository providerRepository;

    public void createProvider(ProviderDto provierDto) {
        Provider provider = new Provider(provierDto.getCompany(),provierDto.getNif(),provierDto.getAddress(), provierDto.getPhone(), provierDto.getEmail(),
                provierDto.getNote());
        this.providerRepository.save(provider);
    }

    public boolean companyRepeated(ProviderDto providerDto) {
        return null != this.providerRepository.findByCompany(providerDto.getCompany());
    }

    public boolean companyRepeated(String id, ProviderDto providerDto) {
        Provider provider = this.providerRepository.findByCompany(providerDto.getCompany());
        return null != provider && !provider.getId().equals(id);
    }

    public boolean existsId(String id) {
        return null != this.providerRepository.findById(id);
    }

    public void putProvider(String id, ProviderDto providerDto) {
        Provider provider = this.providerRepository.findById(id);
        assert provider != null;
        provider.setCompany(providerDto.getCompany());
        provider.setNif(providerDto.getNif());
        provider.setAddress(providerDto.getAddress());
        provider.setPhone(providerDto.getPhone());
        provider.setEmail(providerDto.getEmail());
        provider.setNote(providerDto.getNote());
        provider.setActive(providerDto.getActive());
        this.providerRepository.save(provider);
    }

    public Optional<ProviderDto> readProvider(String id) {
        Provider providerBd = this.providerRepository.findById(id);
        if (providerBd == null) {
            return Optional.empty();
        } else {
            return Optional.of(new ProviderDto(providerBd));
        }
    }

    public List<ProviderMinimumDto> readAll() {
        return this.providerRepository.findMinimumAll();
    }
    
    public List<ProviderMinimumDto> readAllActives() {
        return this.providerRepository.findMinimumAllActives();
    }

}
