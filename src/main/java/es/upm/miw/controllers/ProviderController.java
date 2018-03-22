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
        Provider provider = new Provider(provierDto.getId(),provierDto.getCompany(), provierDto.getAddress(), provierDto.getMobile(), provierDto.getPhone(), provierDto.getNote(), true);
        this.providerRepository.save(provider);
    }
    
    public boolean companyRepeated(ProviderDto providerDto) {
        Provider provider = this.providerRepository.findByCompany(providerDto.getCompany());
        return provider != null 
                && provider.getCompany().equals(providerDto.getCompany()) 
                && !provider.getId().equals(providerDto.getId());
    }
    
    public boolean existsId(String id) {
        return this.providerRepository.findById(id) != null;
    }
    
    public boolean putProvider(String id, ProviderDto providerDto) {
        Provider provider = this.providerRepository.findById(id);
        assert provider != null;
        provider.setCompany(providerDto.getCompany());
        provider.setAddress(providerDto.getAddress());
        provider.setMobile(providerDto.getMobile());
        provider.setPhone(providerDto.getPhone());
        provider.setNote(providerDto.getNote());
        provider.setActive(providerDto.getActive());
        this.providerRepository.save(provider);
        return true;
    }

    public boolean deleteProvider(String id) {
        Provider providerBd = this.providerRepository.findById(id);
        if (providerBd == null) {
            return true;
        } else {
            this.providerRepository.delete(providerBd);
            return true;
        }
    }
    
    public Optional<ProviderDto> readProvider(String id) {
        Provider providerBd = this.providerRepository.findById(id);
        if (providerBd == null) {
            return Optional.empty();
        } else {
            return Optional.of(new ProviderDto(providerBd));
        }
    }

    public List<ProviderMinimumDto> readProviderAll() {
        return this.providerRepository.findProviderAll();
    }

}
